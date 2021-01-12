package com.ruoyi.framework.listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.springframework.stereotype.Component;


@Slf4j
public class TriggerListener implements org.quartz.TriggerListener {

    private String timerTriggerName="";

    public TriggerListener(String timerTriggerName) {
        this.timerTriggerName = timerTriggerName;
    }
    @Override
    public String getName() {
        return timerTriggerName;
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {
        System.out.println("触发器被触发");

    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        System.out.println("触发器被触发shibai");

    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {
        log.info("触发器触发完成");

    }
}
