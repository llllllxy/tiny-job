package org.tinycloud.tinyjob.utils.quartz;

import org.quartz.JobExecutionContext;
import org.tinycloud.tinyjob.bean.entity.TJobInfo;


/**
 * @author liuxingyu01
 * @date 2021-08-27-13:23
 **/
public class QuartzJobExecution extends AbstractQuartzJob {

    @Override
    protected String doExecute(JobExecutionContext context, TJobInfo jobInfo) throws Exception {
        // 开始执行http请求

        return null;
    }
}
