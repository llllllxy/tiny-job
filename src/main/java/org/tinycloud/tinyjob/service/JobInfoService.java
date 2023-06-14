package org.tinycloud.tinyjob.service;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinycloud.tinyjob.bean.entity.TJobInfo;
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



}
