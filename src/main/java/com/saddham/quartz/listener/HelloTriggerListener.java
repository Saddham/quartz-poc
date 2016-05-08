package com.saddham.quartz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.listeners.TriggerListenerSupport;

/**
 * Created by saddhamp on 25/4/16.
 */
public class HelloTriggerListener extends TriggerListenerSupport {
    private static final String name = "HelloTriggerListener";

    public String getName() {
        return name;
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        System.out.println("Trigger fired: "+trigger);
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        System.out.println("Trigger miss fired: "+trigger);
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        if(triggerInstructionCode == Trigger.CompletedExecutionInstruction.SET_TRIGGER_COMPLETE){
            System.out.println("Trigger "+ trigger.getKey().getName()+" completed execution");
            System.out.println("Executed job: "+context.getJobInstance());
        }
    }
}
