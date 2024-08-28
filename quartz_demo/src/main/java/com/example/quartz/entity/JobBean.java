package com.example.quartz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tchstart
 * @data 2024-08-28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobBean {

    /**
     * 任务名字
     */
    private String jobName;

    /**
     * 任务类
     */
    private String jobClass;

    /**
     * cron表达式
     */
    private String cronExpression;
}
