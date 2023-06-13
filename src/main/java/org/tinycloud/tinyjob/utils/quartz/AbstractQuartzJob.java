package org.tinycloud.tinyjob.utils.quartz;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.tinycloud.tinyjob.bean.entity.TJobInfo;
import org.tinycloud.tinyjob.bean.entity.TJobLog;
import org.tinycloud.tinyjob.constant.ScheduleConst;
import org.tinycloud.tinyjob.service.JobLogService;
import org.tinycloud.tinyjob.utils.ExceptionUtil;
import org.tinycloud.tinyjob.utils.SpringContextUtils;

import java.util.Date;


/**
 * 抽象quartz调用
 *
 * @author liuxingyu01
 * @date 2021-08-27-12:45
 **/
public abstract class AbstractQuartzJob implements org.quartz.Job {
    private static final Logger log = LoggerFactory.getLogger(AbstractQuartzJob.class);

    /**
     * 线程本地变量
     */
    private static final ThreadLocal<Date> threadLocal = new ThreadLocal<>();


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        TJobInfo jobInfo = new TJobInfo();
        // 执行结果，即为请求返回的信息
        String result = null;
        BeanUtils.copyProperties(context.getMergedJobDataMap().get(ScheduleConst.TASK_PROPERTIES), jobInfo);
        try {
            before(context, jobInfo);
            if (jobInfo != null) {
                result = doExecute(context, jobInfo);
            }
            after(context, jobInfo, result, null);
        } catch (Exception e) {
            log.error("任务执行异常  - ：", e);
            after(context, jobInfo, null, e);
        }
    }


    /**
     * 执行前
     *
     * @param context 工作执行上下文对象
     * @param job     系统计划任务
     */
    protected void before(JobExecutionContext context, TJobInfo job) {
        threadLocal.set(new Date());
    }

    /**
     * 执行后（记录日志）
     *
     * @param context 工作执行上下文对象
     * @param job     系统计划任务
     */
    protected void after(JobExecutionContext context, TJobInfo job, String result, Exception e) {
        Date startTime = threadLocal.get();
        threadLocal.remove();

        final TJobLog jobLog = new TJobLog();
        jobLog.setJobId(job.getId());
        jobLog.setJobName(job.getJobName());
        jobLog.setJobGroup(job.getJobGroup());
        jobLog.setJobType(job.getJobType());
        jobLog.setJobUrl(job.getJobUrl());
        jobLog.setJobHeader(job.getJobHeader());
        jobLog.setJobParam(job.getJobParam());
        jobLog.setExecuteAt(startTime);
        jobLog.setReturnInfo(result);

        // jobLog.setStartTime(startTime);
        // jobLog.setStopTime(new Date());
        long runMs = startTime.getTime() - new Date().getTime();
        jobLog.setConsuming((int) runMs);
        if (e != null) {
            jobLog.setStatus("1"); // 失败

            String errorMsg = StringUtils.substring(ExceptionUtil.getExceptionMessage(e), 0, 2000);
            jobLog.setExceptionInfo(errorMsg);
        } else {
            jobLog.setStatus("0"); // 成功
        }

        // 写入数据库
        SpringContextUtils.getBean(JobLogService.class).addJobLog(jobLog);
    }


    /**
     * 执行方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param jobInfo 系统计划任务
     * @throws Exception 执行过程中的异常
     */
    protected abstract String doExecute(JobExecutionContext context, TJobInfo jobInfo) throws Exception;
}
