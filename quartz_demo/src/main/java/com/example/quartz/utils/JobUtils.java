package com.example.quartz.utils;

import com.example.quartz.entity.JobBean;
import org.quartz.*;

/**
 * @author tchstart
 * @data 2024-08-28
 */
public class JobUtils {

    private static String PREFIX = "_trigger";

    /**
     * create Job
     * @param scheduler
     * @param jobBean
     */
    public static void createJob(Scheduler scheduler, JobBean jobBean){
        Class<? extends Job> clazz = null;
        JobDetail jobDetail = null;
        Trigger trigger = null;
        try {
            clazz = (Class<? extends Job>) Class.forName(jobBean.getJobClass());
            jobDetail = JobBuilder.newJob(clazz)
                    .storeDurably() // Always exist,even if there are no trigger associated with it.
                    .withIdentity(jobBean.getJobName()) // Set unique identifier.
                    .usingJobData("count", 1) // Initialization of shared data.
                    .build();

            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobBean.getJobName() + PREFIX)
                    .forJob(jobDetail) // Associate JobDetail instance.
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobBean.getCronExpression()))
                    .build();
            scheduler.scheduleJob(jobDetail,trigger);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Pause job
     * @param scheduler
     * @param jobName
     */
    public static void pauseJob(Scheduler scheduler,String jobName){
        JobKey jobKey = JobKey.jobKey(jobName);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Resume job
     * @param scheduler
     * @param jobName
     */
    public static void resumeJob(Scheduler scheduler,String jobName){
        JobKey jobKey = JobKey.jobKey(jobName);
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete job
     * @param scheduler
     * @param jobName
     */
    public static void deleteJob(Scheduler scheduler,String jobName){
        JobKey jobKey = JobKey.jobKey(jobName);
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Run job once
     * @param scheduler
     * @param jobName
     */
    public static void runJobOnce(Scheduler scheduler,String jobName){
        JobKey jobKey = JobKey.jobKey(jobName);
        try {
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update job
     * @param scheduler
     * @param jobBean
     */
    public static void modifyJob(Scheduler scheduler,JobBean jobBean){
        // Get triggerKey unique sign
        TriggerKey triggerKey = TriggerKey.triggerKey(jobBean.getJobName() + PREFIX);
        try {
            // Get trigger by triggerKey
            CronTrigger oldTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // Build a new trigger using a new cron expression
            String newCronExpress = jobBean.getCronExpression();

        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
