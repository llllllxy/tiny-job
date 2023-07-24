package org.tinycloud.tinyjob.utils.quartz;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.tinycloud.tinyjob.bean.entity.TJobInfo;
import org.tinycloud.tinyjob.bean.entity.TJobLog;
import org.tinycloud.tinyjob.bean.pojo.JobResult;
import org.tinycloud.tinyjob.constant.JobLogStatusEnum;
import org.tinycloud.tinyjob.constant.ScheduleConst;
import org.tinycloud.tinyjob.service.JobInfoService;
import org.tinycloud.tinyjob.service.JobLogService;
import org.tinycloud.tinyjob.utils.SpringContextUtils;

import java.io.Serializable;
import java.util.Date;


/**
 * 抽象quartz调用
 * <p>
 * 实现序列化接口、防止重启应用出现quartz Couldn't retrieve job because a required class was not found 的问题
 *
 * @author liuxingyu01
 * @since 2022-08-27-12:45
 **/
public abstract class AbstractQuartzJob implements org.quartz.Job, Serializable {
    private static final Logger log = LoggerFactory.getLogger(AbstractQuartzJob.class);

    private static final long serialVersionUID = 9155949248117098529L;

    /**
     * 线程本地变量
     */
    private static final ThreadLocal<Date> threadLocal = new ThreadLocal<>();


    /**
     * Spring线程池
     */
    private static ThreadPoolTaskExecutor asyncExecutor;

    private static ThreadPoolTaskExecutor getAsyncExecutor() {
        if (asyncExecutor == null) {
            Object bean = SpringContextUtils.getBean("asyncServiceExecutor");
            asyncExecutor = (ThreadPoolTaskExecutor) bean;
        }
        return asyncExecutor;
    }


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        TJobInfo jobInfo = new TJobInfo();
        // 执行结果，即为请求返回的信息
        JobResult result = null;
        BeanUtils.copyProperties(context.getMergedJobDataMap().get(ScheduleConst.JOB_PROPERTIES), jobInfo);
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
    protected void after(JobExecutionContext context, TJobInfo job, JobResult result, Exception e) {
        Date startTime = threadLocal.get();
        threadLocal.remove();

        final TJobLog jobLog = new TJobLog();
        jobLog.setJobId(job.getId()); // 任务ID
        jobLog.setJobName(job.getJobName()); // 任务名
        jobLog.setJobGroup(job.getJobGroup()); // 任务组
        jobLog.setJobType(job.getJobType()); // 请求类型 GET/POST/POST_JSON
        // 这里存最终路由出来的地址
        jobLog.setJobUrl(result == null ? job.getJobUrl() : result.getRouteJobUrl()); // 最终的执行url
        jobLog.setJobHeader(job.getJobHeader()); // 请求头
        jobLog.setJobParam(job.getJobParam()); // 请求参数
        jobLog.setExecuteAt(startTime); // 执行时间
        jobLog.setEndAt(new Date()); // 结束时间
        jobLog.setReturnInfo(result == null ? null : result.getReturnInfo()); // http请求返回的结果

        long consuming = jobLog.getEndAt().getTime() - startTime.getTime(); // 总耗时
        jobLog.setConsuming((int) consuming);
        if (e != null) {
            jobLog.setStatus(JobLogStatusEnum.FAILED.getValue()); // 任务执行结果：失败
            String errorMsg = StringUtils.substring(ExceptionUtils.getStackTrace(e), 0, 2000); // 错误日志
            jobLog.setExceptionInfo(errorMsg);
        } else {
            jobLog.setStatus(JobLogStatusEnum.SUCCESS.getValue()); // 任务执行结果：成功
        }

        // 这里做异步操作，提高性能
        getAsyncExecutor().execute(() -> {
            // 第一步，记录任务执行日志
            SpringContextUtils.getBean(JobLogService.class).addJobLog(jobLog);
            // 第二步、更新t_job_info表的下次执行时间
            SpringContextUtils.getBean(JobInfoService.class).updateNextExecuteTime(job);
        });
    }


    /**
     * 执行方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param jobInfo 系统计划任务
     * @throws Exception 执行过程中的异常
     */
    protected abstract JobResult doExecute(JobExecutionContext context, TJobInfo jobInfo) throws Exception;
}
