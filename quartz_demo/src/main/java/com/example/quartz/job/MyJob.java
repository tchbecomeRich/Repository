package com.example.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

@Slf4j
public class MyJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // context: Environment for timed task execute
        JobDetail jobDetail = context.getJobDetail();
        // Print information
        log.info("TaskName：{}",jobDetail.getKey().getName());
        log.info("GroupName：{}",jobDetail.getKey().getGroup());
        log.info("TaskClass：{}",jobDetail.getJobClass().getName());
        log.info("This execution time：{}",context.getFireTime());
        log.info("Next execution time：{}",context.getNextFireTime());
        // Record
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        Integer count = (Integer) jobDataMap.get("count");
        log.info("count的值为：{}",count);
    }
}
