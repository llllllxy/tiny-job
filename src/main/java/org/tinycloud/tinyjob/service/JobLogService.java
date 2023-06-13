package org.tinycloud.tinyjob.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinycloud.tinyjob.bean.entity.TJobLog;
import org.tinycloud.tinyjob.mapper.JobLogMapper;

@Service
public class JobLogService {

    @Autowired
    private JobLogMapper jobLogMapper;

    public void addJobLog(TJobLog jobLog) {
        jobLogMapper.insert(jobLog);
    }

}
