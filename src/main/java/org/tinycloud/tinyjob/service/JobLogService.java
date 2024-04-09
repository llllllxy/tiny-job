package org.tinycloud.tinyjob.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.tinycloud.tinyjob.bean.dto.JobLogQueryDto;
import org.tinycloud.tinyjob.bean.entity.TJobLog;
import org.tinycloud.tinyjob.bean.entity.TMailConfig;
import org.tinycloud.tinyjob.bean.vo.JobLogQueryVo;
import org.tinycloud.tinyjob.constant.GlobalConstant;
import org.tinycloud.tinyjob.constant.JobLogStatusEnum;
import org.tinycloud.tinyjob.constant.ScheduleConst;
import org.tinycloud.tinyjob.mapper.JobLogMapper;
import org.tinycloud.tinyjob.mapper.MailConfigMapper;
import org.tinycloud.tinyjob.model.PageModel;
import org.tinycloud.tinyjob.utils.EmailUtils;

@Service
public class JobLogService {
    private static final Logger log = LoggerFactory.getLogger(JobLogService.class);

    @Autowired
    private JobLogMapper jobLogMapper;

    @Autowired
    private MailConfigMapper mailConfigMapper;

    public void addJobLog(TJobLog jobLog) {
        String returnInfo = jobLog.getReturnInfo();
        // 如果返回信息长度超过500了，则进行截取，因为数据库里字段长度是varchar(500)
        if (returnInfo != null && returnInfo.length() > 500) {
            returnInfo = returnInfo.substring(0, 500);
            jobLog.setReturnInfo(returnInfo);
        }
        // 计算唯一hash值
        String auditSign = calculateAuditSign(jobLog);
        jobLog.setAuditSign(auditSign);

        jobLogMapper.insert(jobLog);

        // 如果异常，则发送邮件预警
        if (JobLogStatusEnum.FAILED.getValue().equals(jobLog.getStatus())) {
            try {
                TMailConfig mailConfig = mailConfigMapper.selectOne((Wrappers.<TMailConfig>lambdaQuery()
                        .eq(TMailConfig::getDelFlag, GlobalConstant.NOT_DELETED)));
                if (mailConfig == null) {
                    return;
                }
                String emailTitle = "TinyJob任务调度中心-任务预警";
                String emailMsg = "<h2>任务-" + jobLog.getJobName() + "-执行失败！" + "</h2>"
                        + "任务实例ID：" + jobLog.getId() + "<br/>"
                        + "任务实例执行时间：" + DateFormatUtils.format(jobLog.getExecuteAt(), "yyyy-MM-dd HH:mm:ss:SSS") + "<br/>"
                        + "任务ID：" + jobLog.getJobId() + "<br/>"
                        + "请求地址：" + jobLog.getJobUrl() + "<br/>"
                        + "请求方式：" + jobLog.getJobType() + "<br/>"
                        + "请求参数：" + jobLog.getJobParam() + "<br/>"
                        + "请求头：" + jobLog.getJobHeader() + "<br/>"
                        + "异常信息：" + jobLog.getExceptionInfo() + "<br/>";
                EmailUtils.sendMsg(mailConfig.getEmailAccount(), mailConfig.getEmailPassword(), mailConfig.getSmtpAddress(), mailConfig.getSmtpPort(),
                        mailConfig.getReceiveEmail().split(","), emailTitle, emailMsg);
            } catch (Exception e) {
                log.error("addJobLog - sendEmail error: ", e);
            }
        }
    }


    public PageModel<JobLogQueryVo> query(JobLogQueryDto dto) {
        IPage<JobLogQueryVo> pages = this.jobLogMapper.pageList(new Page<>(dto.getPageNo(), dto.getPageSize()), dto);
        PageModel<JobLogQueryVo> pageModel = new PageModel<>(dto.getPageNo(), dto.getPageSize());
        if (pages != null && !CollectionUtils.isEmpty(pages.getRecords())) {
            pageModel.setTotalPage(pages.getPages());
            pageModel.setTotalCount(pages.getTotal());
            pageModel.setRecords(pages.getRecords());
        }
        return pageModel;
    }

    /**
     * 计算日志的签名值
     *
     * @param jobLog 日志信息
     * @return 签名值
     */
    public static String calculateAuditSign(TJobLog jobLog) {
        StringBuilder sb = new StringBuilder();
        sb.append(jobLog.getId());
        sb.append(jobLog.getJobId());
        sb.append(jobLog.getJobUrl());
        sb.append(jobLog.getReturnInfo());
        sb.append(jobLog.getExceptionInfo());
        sb.append(jobLog.getJobParam());
        sb.append(DateFormatUtils.format(jobLog.getExecuteAt(), "yyyy-MM-dd HH:mm:ss:SSS"));
        String content = sb.toString();
        String sign = DigestUtils.sha256Hex(content + ScheduleConst.JOB_LOG_HASH_SALT);
        return sign;
    }
}
