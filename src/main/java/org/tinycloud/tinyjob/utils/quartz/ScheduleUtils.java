package org.tinycloud.tinyjob.utils.quartz;

import org.quartz.*;
import org.tinycloud.tinyjob.bean.entity.TJobInfo;
import org.tinycloud.tinyjob.constant.JobStatusEnum;
import org.tinycloud.tinyjob.constant.JobTriggerEnum;
import org.tinycloud.tinyjob.constant.ScheduleConst;
import org.tinycloud.tinyjob.exception.TaskException;

/**
 * quartz定时任务工具类
 *
 * @author liuxingyu01
 * @since 2022-06-21-13:25
 **/
public class ScheduleUtils {

    /**
     * 得到quartz任务类
     *
     * @param job 执行计划
     * @return 具体执行任务类
     */
    private static Class<? extends org.quartz.Job> getQuartzJobClass(TJobInfo job) {
        boolean isConcurrent = "0".equals(job.getConcurrent());
        return isConcurrent ? QuartzJobExecution.class : QuartzDisallowConcurrentExecution.class;
    }

    /**
     * 构建任务触发对象
     */
    public static TriggerKey getTriggerKey(Long jobId, String triggerGroup) {
        return TriggerKey.triggerKey(ScheduleConst.TRIGGER_PREFIX + jobId, triggerGroup);
    }

    /**
     * 构建任务键对象
     */
    public static JobKey getJobKey(Long jobId, String jobGroup) {
        return JobKey.jobKey(ScheduleConst.JOB_PREFIX + jobId, jobGroup);
    }

    /**
     * 创建定时任务
     */
    public static void createScheduleJob(Scheduler scheduler, TJobInfo job) throws SchedulerException, TaskException {
        // 获取对应的定时任务类 分为可并发和不可并发两种任务 Job 已被自定义抽象类 AbstractQuartzJob 实现
        Class<? extends org.quartz.Job> jobClass = getQuartzJobClass(job);
        // 构建job信息
        Long jobId = job.getId();
        String jobGroup = job.getJobGroup();
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(getJobKey(jobId, jobGroup)).build();

        Trigger trigger;
        if (job.getJobTrigger().equals(JobTriggerEnum.SIMPLE.getCode())) {
            // 简单定时器构建器，可指定任务的间隔时间和重复次数
            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(job.getIntervalSeconds())
                    //.withRepeatCount(repeatCount)
                    .repeatForever(); // 不再限制次数，无限次数

            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(getTriggerKey(jobId, jobGroup))
                    .withSchedule(simpleScheduleBuilder)
                    .build();
        } else {
            // Cron表达式调度构建器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            cronScheduleBuilder = handleCronScheduleMisfirePolicy(job, cronScheduleBuilder);
            // 按新的cronExpression表达式构建一个新的trigger
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(getTriggerKey(jobId, jobGroup))
                    .withSchedule(cronScheduleBuilder)
                    .build();
        }

        // 放入参数，运行时的方法可以获取（AbstractQuartzJob.execute方法）
        jobDetail.getJobDataMap().put(ScheduleConst.JOB_PROPERTIES, job);

        // 判断是否存在，防止创建时存在数据问题 先移除，然后在执行创建操作
        if (scheduler.checkExists(getJobKey(jobId, jobGroup))) {
            scheduler.deleteJob(getJobKey(jobId, jobGroup));
        }
        // 构建并且运行任务
        scheduler.scheduleJob(jobDetail, trigger);

        // 暂停任务
        if (job.getStatus().equals(JobStatusEnum.PAUSE.getValue())) {
            scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
    }


    /**
     * 设置定时任务策略
     */
    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(TJobInfo job, CronScheduleBuilder cb)
            throws TaskException {
        switch (job.getMisfirePolicy()) {
            case ScheduleConst.MISFIRE_DEFAULT:
                return cb;
            case ScheduleConst.MISFIRE_IGNORE_MISFIRES:
                return cb.withMisfireHandlingInstructionIgnoreMisfires();
            case ScheduleConst.MISFIRE_FIRE_AND_PROCEED:
                return cb.withMisfireHandlingInstructionFireAndProceed();
            case ScheduleConst.MISFIRE_DO_NOTHING: // quartz默认的是这个
                return cb.withMisfireHandlingInstructionDoNothing();
            default:
                throw new TaskException("The task misfire policy '" + job.getMisfirePolicy()
                        + "' cannot be used in cron schedule tasks", TaskException.Code.CONFIG_ERROR);
        }
    }


    /**
     * 执行一次
     */
    public static void executeonceScheduler(Scheduler scheduler, TJobInfo job) throws SchedulerException, TaskException {
        try {
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(ScheduleConst.JOB_PROPERTIES, job);
            JobKey jobKey = getJobKey(job.getId(), job.getJobGroup());
            // 如果不存在，则新建一个quartz实例
            if (!scheduler.checkExists(jobKey)) {
                createScheduleJob(scheduler, job);
            }
            scheduler.triggerJob(jobKey, dataMap);
        } catch (SchedulerException e) {
            throw new SchedulerException("执行一次定时任务失败", e);
        }
    }


    /**
     * 删除定时任务
     */
    public static void deleteSchedulerJob(Scheduler scheduler, TJobInfo job) throws SchedulerException {
        try {
            JobKey jobKey = getJobKey(job.getId(), job.getJobGroup());
            if (!scheduler.checkExists(jobKey)) {
                return;
            }
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            throw new SchedulerException("删除定时任务失败", e);
        }
    }


    /**
     * 暂停任务
     */
    public static void pauseSchedulerJob(Scheduler scheduler, TJobInfo job) throws SchedulerException, TaskException {
        try {
            JobKey jobKey = getJobKey(job.getId(), job.getJobGroup());
            if (!scheduler.checkExists(jobKey)) {
                // 任务之前不存在，则新建，新建的同时，createQuartzTask方法内会暂停任务，所以这里无需再次操作
                createScheduleJob(scheduler, job);
                return;
            }
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            throw new SchedulerException("暂停定时任务失败", e);
        }
    }


    /**
     * 恢复任务
     */
    public static void resumeSchedulerJob(Scheduler scheduler, TJobInfo job) throws SchedulerException, TaskException {
        try {
            JobKey jobKey = getJobKey(job.getId(), job.getJobGroup());
            if (!scheduler.checkExists(jobKey)) {
                // 任务之前不存在，则新建，新建的同时，createQuartzTask方法内会自动开始任务，所以这里无需再次操作
                createScheduleJob(scheduler, job);
                return;
            }
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            throw new SchedulerException("恢复定时任务失败", e);
        }
    }


    /**
     * 更新任务（先删除，再新增）
     *
     * @param job       任务对象
     * @param scheduler 任务调度器
     */
    public static void updateSchedulerJob(Scheduler scheduler, TJobInfo job) throws SchedulerException, TaskException {
        // 判断是否存在
        JobKey jobKey = getJobKey(job.getId(), job.getJobGroup());
        if (scheduler.checkExists(jobKey)) {
            // 防止创建时存在数据问题，所以先移除，然后在执行创建操作
            scheduler.deleteJob(jobKey);
        }
        createScheduleJob(scheduler, job);
    }
}
