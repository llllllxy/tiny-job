package org.tinycloud.tinyjob.utils.quartz;

import org.quartz.JobExecutionContext;
import org.tinycloud.tinyjob.bean.entity.TJobInfo;
import org.tinycloud.tinyjob.bean.pojo.JobResult;


/**
 * @author liuxingyu01
 * @date 2021-08-27-13:23
 **/
public class QuartzJobExecution extends AbstractQuartzJob {

    @Override
    protected JobResult doExecute(JobExecutionContext context, TJobInfo jobInfo) throws Exception {
        return JobInvokeUtil.invoke(jobInfo);
    }
}
