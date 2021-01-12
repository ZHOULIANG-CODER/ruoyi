package com.ruoyi.project.monitor.task;

import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.framework.aspectj.lang.annotation.DataSource;
import com.ruoyi.framework.aspectj.lang.enums.DataSourceType;
import com.ruoyi.project.kettle.service.KettleService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import com.ruoyi.common.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@Component("ryTask")

public class RyTask
{
    private  static Logger logger =LoggerFactory.getLogger(RyTask.class);
    private  static ConcurrentHashMap<String,Future<?>> map=new ConcurrentHashMap();
    private   KettleService kettleService =  SpringUtils.getBean("kettleService");
    private ThreadPoolTaskExecutor threadTaskExecutor=SpringUtils.getBean("threadTaskExecutor");
    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }


    public void ryParams(String params) throws ExecutionException, InterruptedException {
        Map<String,String> param =new HashMap<>(1);
        param.put("param1",params);

                Future<?> submit = threadTaskExecutor.submit(new Runnable() {
                    @Override
                    public void run() {
                            kettleService.runKjb("test.kjb", param);
                            logger.info("id====================="+Thread.currentThread().getId());
                            logger.info("id====================="+Thread.currentThread().getName());
                    }
                });




    }


            // 无参 调度任务
    public void ryNoParams() {
            // 调用kettle
        kettleService.runKtr("test.ktr",null);

    }
}
