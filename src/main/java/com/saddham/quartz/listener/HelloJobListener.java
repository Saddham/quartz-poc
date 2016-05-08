package com.saddham.quartz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;

/**
 * Created by saddhamp on 25/4/16.
 */
public class HelloJobListener extends JobListenerSupport{
    private static final String name = "HelloJobListener";

    public String getName() {
        return name;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        System.out.println("About to execute: "+context.getJobDetail().getKey().getName());
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        System.out.println("Vetoed: "+context.getJobDetail().getKey().getName()+" by the trigger "+
            context.getTrigger().getKey().getName());
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        if(jobException == null) {
            System.out.println("Executed: " + context.getJobDetail().getKey().getName());
            System.out.println("Fire instance id: " + context.getFireInstanceId());
            System.out.println("Fire time: " + context.getFireTime());
            System.out.println("Previous fire time: " + context.getPreviousFireTime());
            System.out.println("Next fire time: " + context.getNextFireTime());
            System.out.println("Triggered by: " + context.getTrigger());
            System.out.println("Refire count: " + context.getRefireCount());
        } else{
            System.out.println("Exception occured while executing job "+"("+
                    context.getJobDetail().getKey().getName()+"): \n"+jobException);
        }
    }
}
