package com.saddham.quartz.app;

import java.util.Date;
import java.util.TimeZone;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Matcher;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.HolidayCalendar;
import org.quartz.utils.Key;

import com.saddham.quartz.job.HelloJob;
import com.saddham.quartz.listener.HelloJobListener;
import com.saddham.quartz.listener.HelloSchedulerListener;
import com.saddham.quartz.listener.HelloTriggerListener;

import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.impl.matchers.KeyMatcher.*;
import static org.quartz.impl.matchers.AndMatcher.and;
import static org.quartz.impl.matchers.NameMatcher.*;
import static org.quartz.impl.matchers.GroupMatcher.*;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;

/**
 * Created by saddhamp on 25/4/16.
 */
public class HelloQuartzApp {

    public static void main(String [] args){
        System.out.println(new Date());

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = null;
        try {
            scheduler = schedulerFactory.getScheduler();
            scheduler.start();

            HolidayCalendar quartzHolidayCalendar = new HolidayCalendar();
            quartzHolidayCalendar.addExcludedDate(new Date());
            scheduler.addCalendar("HolidayCalendar", quartzHolidayCalendar, false, true);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("firstName", "Saddham");
        jobDataMap.put("lastName", "Pathan");

        JobKey jobKey = new JobKey("HelloJob", "GreetingGroup");
        JobDetail jobDetail = newJob(HelloJob.class)
                .withIdentity(jobKey)
                .usingJobData(jobDataMap)
                .storeDurably(true)
                .requestRecovery(true)
                .build();

        TriggerKey triggerKey = new TriggerKey("HelloTrigger", "GreetingGroup");
        Trigger trigger = newTrigger()
                .withIdentity(triggerKey)
                .forJob(jobKey)
                .startNow()
                //.startAt(evenHourDate(null))
                //Every 5 seconds after 7:12 PM IST
                .withSchedule(cronSchedule("0/5 12 19 * * ?").inTimeZone(TimeZone.getTimeZone("IST")))
                //.modifiedByCalendar("HolidayCalendar")
                .withPriority(10)
                .build();

        HelloSchedulerListener helloSchedulerListener = new HelloSchedulerListener();
        try {

            scheduler.getListenerManager().addJobListener(new HelloJobListener(), keyEquals(jobKey));
            scheduler.getListenerManager().addTriggerListener(new HelloTriggerListener(),
                    and(triggerNameEquals("HelloTrigger"), triggerGroupEquals("GreetingGroup")));
            scheduler.getListenerManager().addSchedulerListener(helloSchedulerListener);
            scheduler.addJob(jobDetail, true);
            scheduler.scheduleJob(trigger);
            Thread.sleep(60*1000L);
        } catch (SchedulerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                scheduler.deleteJob(jobKey);
                scheduler.getListenerManager().removeJobListener("HelloJobListener");
                scheduler.getListenerManager().removeTriggerListener("HelloTriggerListener");
                scheduler.getListenerManager().removeSchedulerListener(helloSchedulerListener);
                scheduler.shutdown(true);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }

    }
}
