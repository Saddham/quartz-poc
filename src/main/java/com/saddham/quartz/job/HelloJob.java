package com.saddham.quartz.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by saddhamp on 25/4/16.
 */
@DisallowConcurrentExecution
public class HelloJob implements Job {
    private String firstName;
    private String lastName;

  /*  HelloJob newInstance(){
        return new HelloJob();
    }
*/
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Hello "+firstName+" "+lastName);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
