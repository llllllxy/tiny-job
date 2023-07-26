package org.tinycloud.tinyjob.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.tinycloud.tinyjob.bean.dto.JobLogQueryDto;
import org.tinycloud.tinyjob.bean.entity.THostsItem;
import org.tinycloud.tinyjob.bean.entity.TJobLog;
import org.tinycloud.tinyjob.bean.entity.TMailConfig;
import org.tinycloud.tinyjob.bean.vo.JobLogQueryVo;
import org.tinycloud.tinyjob.constant.GlobalConstant;
import org.tinycloud.tinyjob.constant.JobLogStatusEnum;
import org.tinycloud.tinyjob.mapper.JobLogMapper;
import org.tinycloud.tinyjob.mapper.MailConfigMapper;
import org.tinycloud.tinyjob.model.PageModel;
import org.tinycloud.tinyjob.utils.EmailUtils;

@Service
public class JobLogService {

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
        jobLogMapper.insert(jobLog);

        // 如果异常，则发送邮件预警
        if (JobLogStatusEnum.FAILED.getValue().equals(jobLog.getStatus())) {
            try {
                TMailConfig mailConfig = mailConfigMapper.selectOne((Wrappers.<TMailConfig>lambdaQuery()
                        .eq(TMailConfig::getDelFlag, GlobalConstant.NOT_DELETED)));
                if (mailConfig == null) {
                    return;
                }
                String emailTitle = "TinyJob任务调度中心预-任务预警";
                String emailMsg = "<h2>您好，感谢您在蓝风短链注册账户！</h2>"
                        + "任务-" + jobLog.getJobName() + "-执行失败！" + "<br/>"
                        + "任务实例ID：" + jobLog.getId() + "<br/>"
                        + "任务实例执行时间：" + jobLog.getExecuteAt() + "<br/>"
                        + "任务ID：" + jobLog.getJobId() + "<br/>"
                        + "请求地址：" + jobLog.getJobUrl() + "<br/>"
                        + "请求方式：" + jobLog.getJobType() + "<br/>"
                        + "请求参数：" + jobLog.getJobParam() + "<br/>"
                        + "请求头：" + jobLog.getJobHeader() + "<br/>"
                        + "异常信息：" + jobLog.getExceptionInfo() + "<br/>";
                EmailUtils.sendMsg(mailConfig.getEmailAccount(), mailConfig.getEmailPassword(), mailConfig.getSmtpAddress(), mailConfig.getSmtpPort(),
                        mailConfig.getReceiveEmail().split(","), emailTitle, emailMsg);
            } catch (Exception ignored) {

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
}
