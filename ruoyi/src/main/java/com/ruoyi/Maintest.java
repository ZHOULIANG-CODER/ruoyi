package com.ruoyi;


import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.monitor.task.HelloTask;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;
import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class Maintest {
    public static void main(String[] args) throws SchedulerException, ParseException {
//
//
//            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
//        // define the job and tie it to our HelloJob class
//        JobDetail job = newJob(HelloTask.class)
//                .withIdentity("job1", "group1")
//                .build();
//
//        // Trigger the job to run now, and then repeat every 40 seconds
//        Trigger trigger = newTrigger()
//                .withIdentity("trigger1", "group1")
//                .startNow()
//                .withSchedule(simpleSchedule()
//                        .withIntervalInSeconds(5)
//                        .repeatForever())
//                .build();
//
//        // Tell quartz to schedule the job using our trigger
//                    scheduler.scheduleJob(job, trigger);
//                    scheduler.start();
//                    scheduler.shutdown();
//
//        Date date = DateUtils.parseDate("20200101",DateUtils.YYYY_MM_DD);
        final int minuteStart = 2;
        final int hourStart = 0;
        String time="0500";

        String cronStr = "0 ".concat(time.substring(minuteStart)).concat(" ")
                .concat(time.substring(hourStart, minuteStart)).concat(" * * ?");
        System.out.println("____________"+cronStr);
    }

}
