package org.tinycloud.tinyjob.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinycloud.security.util.AuthUtil;
import org.tinycloud.tinyjob.bean.dto.JobInfoAddDto;
import org.tinycloud.tinyjob.bean.entity.TJobInfo;
import org.tinycloud.tinyjob.constant.GlobalConstant;
import org.tinycloud.tinyjob.constant.JobStatusEnum;
import org.tinycloud.tinyjob.exception.TaskException;
import org.tinycloud.tinyjob.mapper.JobInfoMapper;
import org.tinycloud.tinyjob.utils.BeanConvertUtils;
import org.tinycloud.tinyjob.utils.quartz.CronUtils;
import org.tinycloud.tinyjob.utils.quartz.ScheduleUtils;

import java.util.Date;

@Service
public class JobInfoService {

    @Autowired
    private JobInfoMapper jobInfoMapper;

    @Autowired
    private Scheduler scheduler;

    /**
     * 执行一次
     *
     * @param id 任务id
     * @return
     */
    public void executeonce(Long id) throws SchedulerException, TaskException {
        TJobInfo jobInfo = jobInfoMapper.selectById(id);
        ScheduleUtils.executeonceScheduler(scheduler, jobInfo);
    }


    /**
     * 启动任务
     *
     * @param id 任务id
     * @return
     */
    public int start(Long id) throws SchedulerException, TaskException {
        TJobInfo jobInfo = jobInfoMapper.selectById(id);

        LambdaUpdateWrapper<TJobInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(TJobInfo::getId, id);
        wrapper.set(TJobInfo::getStatus, JobStatusEnum.NORMAL.getValue());
        wrapper.set(TJobInfo::getUpdatedBy, AuthUtil.getLoginId());

        int rows = jobInfoMapper.update(null, wrapper);
        if (rows > 0) {
            ScheduleUtils.resumeSchedulerJob(scheduler, jobInfo);
        }
        return rows;
    }


    /**
     * 停止任务
     *
     * @param id 任务id
     * @return
     */
    public int stop(Long id) throws SchedulerException, TaskException {
        TJobInfo jobInfo = jobInfoMapper.selectById(id);

        LambdaUpdateWrapper<TJobInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(TJobInfo::getId, id);
        wrapper.set(TJobInfo::getStatus, JobStatusEnum.PAUSE.getValue());
        wrapper.set(TJobInfo::getUpdatedBy, AuthUtil.getLoginId());

        int rows = jobInfoMapper.update(null, wrapper);
        if (rows > 0) {
            ScheduleUtils.pauseSchedulerJob(scheduler, jobInfo);
        }
        return rows;
    }


    /**
     * 删除任务
     *
     * @param id 任务id
     * @return
     */
    public int delete(Long id) throws SchedulerException, TaskException {
        TJobInfo jobInfo = jobInfoMapper.selectById(id);
        // 逻辑删除
        LambdaUpdateWrapper<TJobInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(TJobInfo::getId, id);
        wrapper.set(TJobInfo::getDelFlag, GlobalConstant.DELETED);
        wrapper.set(TJobInfo::getUpdatedBy, (String) AuthUtil.getLoginId());

        int rows = jobInfoMapper.update(null, wrapper);
        if (rows > 0) {
            ScheduleUtils.deleteSchedulerJob(scheduler, jobInfo);
        }
        return rows;
    }

    /**
     * 新增任务
     *
     * @param dto 任务id
     * @return
     */
    public int add(JobInfoAddDto dto) throws SchedulerException, TaskException {
        TJobInfo jobInfo = BeanConvertUtils.convertTo(dto, TJobInfo::new);
        jobInfo.setStatus(JobStatusEnum.PAUSE.getValue());
        jobInfo.setCreatedBy((String) AuthUtil.getLoginId());
        jobInfo.setDelFlag(GlobalConstant.NOT_DELETED);
        jobInfo.setNextExecuteTime(CronUtils.getNextExecution(jobInfo.getCronExpression()));

        int rows = jobInfoMapper.insert(jobInfo);
        if (rows > 0) {
            ScheduleUtils.createScheduleJob(scheduler, jobInfo);
        }

        return rows;
    }

    /**
     * 更新下次执行时间
     *
     * @param id 任务id
     * @param cronExpression cron表达式
     * @return int
     */
    public int updateNextExecuteTime(Long id, String cronExpression) {
        Date nextExecuteTime = CronUtils.getNextExecution(cronExpression);
        LambdaUpdateWrapper<TJobInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(TJobInfo::getId, id);
        wrapper.set(TJobInfo::getNextExecuteTime, nextExecuteTime);
        return jobInfoMapper.update(null, wrapper);
    }
}
