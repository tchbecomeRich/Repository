package com.example.quartz.config;

import com.example.quartz.job.MyJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail jobDetail(){
        return JobBuilder.newJob(MyJob.class)
                .storeDurably() // Always exist,even if there are no trigger associated with it.
                .withIdentity("job1","group1") // Set unique identifier.
                .usingJobData("count",1) // Initialization of shared data.
                .build();
    }

    @Bean
    public Trigger trigger(){
        String croExpression = "0/2 * * * * ? *"; // Execute every two second.
        return TriggerBuilder.newTrigger()
                .withIdentity("trigger1","group1")
                .forJob(jobDetail()) // Associate JobDetail instance.
                .withSchedule(CronScheduleBuilder.cronSchedule(croExpression))
                .build();
    }

}
