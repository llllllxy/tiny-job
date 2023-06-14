package org.tinycloud.tinyjob.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinycloud.security.util.AuthUtil;
import org.tinycloud.tinyjob.bean.entity.TJobInfo;
import org.tinycloud.tinyjob.constant.JobStatusEnum;
import org.tinycloud.tinyjob.exception.TaskException;
import org.tinycloud.tinyjob.mapper.JobInfoMapper;
import org.tinycloud.tinyjob.utils.quartz.ScheduleUtils;

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

}
