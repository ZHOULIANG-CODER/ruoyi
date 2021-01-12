package com.ruoyi;

import com.ruoyi.common.exception.TaskException;
import com.ruoyi.common.utils.ScheduleUtils;
import com.ruoyi.project.monitor.domain.SysJob;
import com.ruoyi.project.monitor.mapper.SysJobMapper;
import org.aspectj.lang.annotation.After;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.EverythingMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableAsync

public class RuoYiApplication
{
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private SysJobMapper jobMapper;
    @Autowired
    PlatformTransactionManager transactionManager;
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(RuoYiApplication.class, args);

    }
    // 观察自动的事务管理器
    @PostConstruct
    public  void xxx(){
        System.out.println("事务----------"+transactionManager.getClass().getName());
    }

    @Bean
    public void init() throws SchedulerException, TaskException
    {
        scheduler.clear();
        List<SysJob> jobList = jobMapper.selectJobAll();

        for (SysJob job : jobList)
        {
            ScheduleUtils.createScheduleJob(scheduler, job);
        }

    }
}
