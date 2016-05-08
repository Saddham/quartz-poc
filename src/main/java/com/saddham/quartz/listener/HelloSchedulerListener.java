package com.saddham.quartz.listener;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.listeners.SchedulerListenerSupport;

/**
 * Created by saddhamp on 25/4/16.
 */
public class HelloSchedulerListener extends SchedulerListenerSupport {
    @Override
    public void jobAdded(JobDetail jobDetail) {
        System.out.println("Job added: "+jobDetail.getKey().getName());
    }

    @Override
    public void jobDeleted(JobKey jobKey) {
        System.out.println("Job deleted: "+jobKey.getName());
    }

    @Override
    public void jobScheduled(Trigger trigger) {
        System.out.println("Job triggered: "+trigger.getKey().getName());
    }

    @Override
    public void schedulerStarted() {
        System.out.println("Scheduler started");
    }

    @Override
    public void schedulerStarting() {
        System.out.println("Starting scheduler");
    }

    @Override
    public void triggerFinalized(Trigger trigger) {
        System.out.println("Finalized trigger: "+trigger.getKey().getName());
    }
}
