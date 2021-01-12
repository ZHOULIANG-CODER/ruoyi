package com.ruoyi.framework.listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;



/**
 * @Description
 * 删除一个job/trigger，scheduler发生严重错误，关闭scheduler等。
 * SchedulerListener会在Scheduler的生命周期中关键事件发生时被调用。
 * 与Scheduler有关的事件包括：增加一个job/trigger
 * @author zhouliang
 * @date 2021/1/11 16:28 
 * @Version 3.0.6.1
 */ 
@Slf4j
public class TimerSchedulerListener implements SchedulerListener {
    @Override
    public void jobScheduled(Trigger trigger) {
        String jobName = trigger.getJobKey().getName();
        log.info(jobName +"部署");
        System.out.println(jobName + "部署s");
    }

    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
        log.info(triggerKey + "卸载");
        System.out.println(triggerKey + "卸载");
    }

    @Override
    public void triggerFinalized(Trigger trigger) {
        log.info("触发完成" + trigger.getJobKey().getName());
        System.out.println("触发完成" + trigger.getJobKey().getName());
    }

    @Override
    public void triggerPaused(TriggerKey triggerKey) {
        log.info(triggerKey + "暂停");
        System.out.println(triggerKey + "暂停");
    }

    @Override
    public void triggersPaused(String triggerGroup) {
        log.info("trigger group "+triggerGroup + "暂停");
        System.out.println("trigger group "+triggerGroup + "暂停");
    }

    @Override
    public void triggerResumed(TriggerKey triggerKey) {
        log.info(triggerKey + "从暂停中恢复");
        System.out.println(triggerKey + "从暂停中恢复");
    }

    @Override
    public void triggersResumed(String triggerGroup) {
        log.info("trigger group "+triggerGroup + "从暂停中恢复");
        System.out.println("trigger group "+triggerGroup + "从暂停中恢复");
    }

    @Override
    public void jobAdded(JobDetail jobDetail) {
        log.info(jobDetail.getKey()+"增加");
        System.out.println(jobDetail.getKey()+"增加");
    }

    @Override
    public void jobDeleted(JobKey jobKey) {
        log.info(jobKey+"删除");
        System.out.println(jobKey+"删除");
    }

    @Override
    public void jobPaused(JobKey jobKey) {
        log.info(jobKey+"暂停");
        System.out.println(jobKey+"暂停");
    }

    @Override
    public void jobsPaused(String jobGroup) {
        log.info("job group "+jobGroup+"暂停");
        System.out.println("job group "+jobGroup+"暂停");
    }

    @Override
    public void jobResumed(JobKey jobKey) {
        log.info(jobKey+"从暂停上恢复");
        System.out.println(jobKey+"从暂停上恢复");
    }

    @Override
    public void jobsResumed(String jobGroup) {
        log.info("job group "+jobGroup+"从暂停上恢复");
        System.out.println("job group "+jobGroup+"从暂停上恢复");
    }

    @Override
    public void schedulerError(String msg, SchedulerException cause) {
        log.error(msg, cause.getUnderlyingException());
        log.info("正常运行期间产生一个严重错误");
        System.out.println("正常运行期间产生一个严重错误");
    }

    @Override
    public void schedulerInStandbyMode() {
        log.info("Scheduler处于StandBy模式");
        System.out.println("Scheduler处于StandBy模式");
    }

    @Override
    public void schedulerStarted() {
        log.info("scheduler开启完成");
        System.out.println("scheduler开启完成");
    }

    @Override
    public void schedulerStarting() {
        log.info("scheduler正在开启");
        System.out.println("scheduler正在开启");
    }

    @Override
    public void schedulerShutdown() {
        log.info("scheduler停止");
        System.out.println("scheduler停止");
    }

    @Override
    public void schedulerShuttingdown() {
        log.info("scheduler正在停止");
        System.out.println("scheduler正在停止");
    }

    @Override
    public void schedulingDataCleared() {
        log.info("Scheduler中的数据被清除");
        System.out.println("Scheduler中的数据被清除");
    }
}
