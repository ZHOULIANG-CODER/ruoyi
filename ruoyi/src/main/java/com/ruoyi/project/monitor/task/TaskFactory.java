package com.ruoyi.project.monitor.task;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@Slf4j
public class TaskFactory implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        log.info("====================================helloJob 测试");
    }
}
