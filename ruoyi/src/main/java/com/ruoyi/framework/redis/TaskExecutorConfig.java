package com.ruoyi.framework.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class TaskExecutorConfig {

    /**
     * 创建一个线程池
     * @return
     */
    @Bean(name = "threadTaskExecutor")
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);   //核心线程池大小
        executor.setMaxPoolSize(50);    //最大线程池大小
        executor.setQueueCapacity(1000);    //任务队列大小
        executor.setKeepAliveSeconds(300);  //线程池中空闲线程等待工作的超时时间（单位秒）
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());    //线程拒绝策略，此策略提供简单的反馈控制机制，能够减缓新任务的提交速度

        return executor;
    }

    /**
     * 创建一个固定大小的线程池
     * @return
     */
    @Bean(name = "fixedThreadPool")
    public ExecutorService executorService(){
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        return fixedThreadPool;
    }

}