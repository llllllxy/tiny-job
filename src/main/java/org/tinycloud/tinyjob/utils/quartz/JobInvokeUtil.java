package org.tinycloud.tinyjob.utils.quartz;

import org.tinycloud.tinyjob.bean.entity.TJobInfo;
import org.tinycloud.tinyjob.bean.pojo.JobResult;
import org.tinycloud.tinyjob.utils.SpringContextUtils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 任务执行工具（根据策略，执行http请求，）
 *
 * @author liuxingyu01
 * @since 2022-06-14 13:19
 **/
public class JobInvokeUtil {
    private static final Logger log = LoggerFactory.getLogger(JobInvokeUtil.class);

    public static JobResult invoke(TJobInfo jobInfo) throws Exception {
        log.info("invoke jobInfo: {}", jobInfo);


        return null;
    }

}
