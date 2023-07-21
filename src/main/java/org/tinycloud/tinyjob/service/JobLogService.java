package org.tinycloud.tinyjob.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.tinycloud.tinyjob.bean.dto.JobLogQueryDto;
import org.tinycloud.tinyjob.bean.entity.TJobLog;
import org.tinycloud.tinyjob.bean.vo.JobLogQueryVo;
import org.tinycloud.tinyjob.mapper.JobLogMapper;
import org.tinycloud.tinyjob.model.PageModel;

@Service
public class JobLogService {

    @Autowired
    private JobLogMapper jobLogMapper;

    public void addJobLog(TJobLog jobLog) {
        String returnInfo = jobLog.getReturnInfo();
        // 如果返回信息长度超过500了，则进行截取，因为数据库里字段长度是varchar(500)
        if (returnInfo != null && returnInfo.length() > 500) {
            returnInfo = returnInfo.substring(0, 500);
            jobLog.setReturnInfo(returnInfo);
        }
        jobLogMapper.insert(jobLog);
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
