package com.example.quartz.controller;

import com.example.quartz.entity.JobBean;
import com.example.quartz.job.MyJob;
import com.example.quartz.utils.JobUtils;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tchstart
 * @data 2024-08-28
 */
@RestController
@RequestMapping("/quartz")
public class QuartzController {

    @Autowired
    private Scheduler scheduler;

    private String jobName = "myjob";
    String croExpression = "0/2 * * * * ? *"; // Execute every two second.

    @GetMapping("/create")
    public String createJob(){
        JobBean jobBean = new JobBean(jobName, MyJob.class.getName(),croExpression);
        JobUtils.createJob(scheduler,jobBean);
        return "Create job success!";
    }

    @GetMapping("/pause")
    public String pauseJob(){
        JobUtils.pauseJob(scheduler,jobName);
        return "Pause job success!";
    }

    @GetMapping("resume")
    public String resumeJob(){
        JobUtils.resumeJob(scheduler,jobName);
        return "Resume job success!";
    }

    @GetMapping("/delete")
    public String deleteJob(){
        JobUtils.deleteJob(scheduler,jobName);
        return "Delete job success";
    }

    @GetMapping("/once")
    public String runOnceJob(){
        JobUtils.runJobOnce(scheduler,jobName);
        return "Job invoke once success!";
    }
}
