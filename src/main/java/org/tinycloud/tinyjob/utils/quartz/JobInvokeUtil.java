package org.tinycloud.tinyjob.utils.quartz;

import org.springframework.util.CollectionUtils;
import org.tinycloud.tinyjob.bean.entity.TJobInfo;
import org.tinycloud.tinyjob.bean.pojo.JobResult;
import org.tinycloud.tinyjob.service.HostsInfoService;
import org.tinycloud.tinyjob.utils.JsonUtils;
import org.tinycloud.tinyjob.utils.SpringContextUtils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tinycloud.tinyjob.utils.http.HttpStrategy;
import org.tinycloud.tinyjob.utils.route.ExecutorRouteStrategyEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 任务执行工具（根据策略，执行http请求，）
 *
 * @author liuxingyu01
 * @since 2022-06-14 13:19
 **/
public class JobInvokeUtil {
    private static final Logger log = LoggerFactory.getLogger(JobInvokeUtil.class);

    public static JobResult invoke(TJobInfo jobInfo) throws Exception {
        // log.info("invoke jobInfo: {}", jobInfo);

        // 第一步，根据host_id 获取最终负载均衡的地址
        Long hostId = jobInfo.getHostId();
        List<String> hosts = SpringContextUtils.getBean(HostsInfoService.class).getAddrs(hostId);
        if (CollectionUtils.isEmpty(hosts)) {
            throw new Exception("主机地址未配置，无法执行任务！");
        }
        ExecutorRouteStrategyEnum strategyEnum = ExecutorRouteStrategyEnum.match(jobInfo.getStrategy(), ExecutorRouteStrategyEnum.ROUND);
        String finalHost = strategyEnum.getRouter().route(hosts, jobInfo.getId());
        String finalUrl = finalHost + jobInfo.getJobUrl();

        // 第二步、根据job_type确定发起请求
        String jobType = jobInfo.getJobType();
        String jobParam = jobInfo.getJobParam();
        String jobHeader = jobInfo.getJobHeader();
        if (StringUtils.isEmpty(jobParam)) {
            jobParam = "{}";
        }
        if (StringUtils.isEmpty(jobHeader)) {
            jobHeader = "{}";
        }
        Map<String, Object> paramMap = JsonUtils.readValue(jobParam, Map.class);
        Map<String, Object> headerMap = JsonUtils.readValue(jobHeader, Map.class);
        Map<String, Object> otherMap = new HashMap<>();
        otherMap.put("failRetryTimes", jobInfo.getFailRetryTimes());
        otherMap.put("executorTimeout", jobInfo.getExecutorTimeout());

        // 策略模式调用，更加高级
        String returnInfo = HttpStrategy.getResult(jobType, finalUrl, paramMap, headerMap, otherMap);

        // 这里可以根据returnInfo里包含的返回值来进行判断任务是否成功或者失败（抛出Exception异常即可），可以根据业务进行个性化定制

        // 第三步、组织接口信息，返回执行结果
        JobResult jobResult = new JobResult();
        jobResult.setReturnInfo(returnInfo);
        jobResult.setRouteJobUrl(finalUrl);

        return jobResult;
    }

}
