package com.ruoyi.project.monitor.execution;

import com.ruoyi.common.utils.JobInvokeUtil;
import com.ruoyi.project.monitor.domain.SysJob;
import com.ruoyi.project.monitor.job.AbstractQuartzJob;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（允许并发执行）
 * 
 * @author ruoyi
 *
 */
public class QuartzJobExecution extends AbstractQuartzJob
{
    @Override
    protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception
    {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
