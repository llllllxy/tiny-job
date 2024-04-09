package org.tinycloud.tinyjob.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.quartz.CronExpression;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.tinycloud.tinyjob.bean.dto.JobInfoAddDto;
import org.tinycloud.tinyjob.bean.dto.JobInfoEditDto;
import org.tinycloud.tinyjob.bean.dto.JobInfoQueryDto;
import org.tinycloud.tinyjob.bean.entity.TJobInfo;
import org.tinycloud.tinyjob.bean.vo.JobInfoQueryVo;
import org.tinycloud.tinyjob.bean.vo.JobInfoSelectVo;
import org.tinycloud.tinyjob.constant.ApiErrorCode;
import org.tinycloud.tinyjob.constant.GlobalConstant;
import org.tinycloud.tinyjob.constant.JobStatusEnum;
import org.tinycloud.tinyjob.constant.JobTriggerEnum;
import org.tinycloud.tinyjob.exception.BusinessException;
import org.tinycloud.tinyjob.exception.TaskException;
import org.tinycloud.tinyjob.mapper.JobInfoMapper;
import org.tinycloud.tinyjob.model.PageModel;
import org.tinycloud.tinyjob.utils.AuthUtils;
import org.tinycloud.tinyjob.utils.BeanConvertUtils;
import org.tinycloud.tinyjob.utils.JsonUtils;
import org.tinycloud.tinyjob.utils.quartz.CronUtils;
import org.tinycloud.tinyjob.utils.quartz.ScheduleUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobInfoService {

    @Autowired
    private JobInfoMapper jobInfoMapper;

    @Autowired
    private Scheduler scheduler;

    /**
     * 分页查询
     *
     * @param dto
     * @return
     */
    public PageModel<JobInfoQueryVo> query(JobInfoQueryDto dto) {
        IPage<JobInfoQueryVo> pages = jobInfoMapper.pageList(new Page<>(dto.getPageNo(), dto.getPageSize()), dto);
        PageModel<JobInfoQueryVo> pageModel = new PageModel<>(dto.getPageNo(), dto.getPageSize());
        if (pages != null && !CollectionUtils.isEmpty(pages.getRecords())) {
            pageModel.setTotalPage(pages.getPages());
            pageModel.setTotalCount(pages.getTotal());
            pageModel.setRecords(pages.getRecords());
        }
        return pageModel;
    }

    /**
     * 根据主机id获取任务列表
     *
     * @param hostId 主机id
     * @return
     */
    public List<JobInfoSelectVo> select(Long hostId) {
        QueryWrapper<TJobInfo> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .select(TJobInfo::getId, TJobInfo::getJobName)
                .eq(TJobInfo::getDelFlag, GlobalConstant.NOT_DELETED)
                .eq(TJobInfo::getHostId, hostId);

        List<TJobInfo> items = this.jobInfoMapper.selectList(wrapper);
        if (items != null && !items.isEmpty()) {
            return items.stream().map(item -> {
                JobInfoSelectVo vo = new JobInfoSelectVo();
                vo.setJobName(item.getJobName());
                vo.setId(item.getId());
                return vo;
            }).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }


    /**
     * 执行一次
     *
     * @param id 任务id
     * @return
     */
    public void executeonce(Long id) throws SchedulerException, TaskException {
        TJobInfo jobInfo = jobInfoMapper.selectById(id);
        ScheduleUtils.executeOnceSchedulerJob(scheduler, jobInfo);
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
        wrapper.set(TJobInfo::getUpdatedBy, AuthUtils.getLoginId());

        int rows = jobInfoMapper.update(null, wrapper);
        if (rows > 0) {
            ScheduleUtils.resumeSchedulerJob(scheduler, jobInfo);
        }
        return rows;
    }


    /**
     * 停止任务
     *
     * @param id 任务id
     * @return
     */
    public int stop(Long id) throws SchedulerException, TaskException {
        TJobInfo jobInfo = jobInfoMapper.selectById(id);

        LambdaUpdateWrapper<TJobInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(TJobInfo::getId, id);
        wrapper.set(TJobInfo::getStatus, JobStatusEnum.PAUSE.getValue());
        wrapper.set(TJobInfo::getUpdatedBy, AuthUtils.getLoginId());

        int rows = jobInfoMapper.update(null, wrapper);
        if (rows > 0) {
            ScheduleUtils.pauseSchedulerJob(scheduler, jobInfo);
        }
        return rows;
    }


    /**
     * 删除任务
     *
     * @param id 任务id
     * @return
     */
    public int delete(Long id) throws SchedulerException, TaskException {
        TJobInfo jobInfo = jobInfoMapper.selectById(id);
        // 逻辑删除
        LambdaUpdateWrapper<TJobInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(TJobInfo::getId, id);
        wrapper.set(TJobInfo::getDelFlag, GlobalConstant.DELETED);
        wrapper.set(TJobInfo::getUpdatedBy, (String) AuthUtils.getLoginId());

        int rows = jobInfoMapper.update(null, wrapper);
        if (rows > 0) {
            ScheduleUtils.deleteSchedulerJob(scheduler, jobInfo);
        }
        return rows;
    }

    /**
     * 新增任务
     *
     * @param dto 任务id
     * @return
     */
    @Transactional
    public int add(JobInfoAddDto dto) throws SchedulerException, TaskException {
        if (dto.getJobTrigger().equals(JobTriggerEnum.CRON.getCode())) {
            if (!StringUtils.hasText(dto.getCronExpression()) || !CronExpression.isValidExpression(dto.getCronExpression())) {
                throw new BusinessException(ApiErrorCode.CRON_EXPRESSION_ERROR.getCode(), ApiErrorCode.CRON_EXPRESSION_ERROR.getDesc());
            }
        } else {
            if (dto.getIntervalSeconds() == null || dto.getIntervalSeconds() == 0) {
                throw new BusinessException(ApiErrorCode.JOB_INTERVAL_SECONDS_ERROR.getCode(), ApiErrorCode.JOB_INTERVAL_SECONDS_ERROR.getDesc());
            }
        }
        if (StringUtils.hasText(dto.getJobParam()) && !JsonUtils.isJsonValid(dto.getJobParam())) {
            throw new BusinessException(ApiErrorCode.JOB_PARAM_FORMAT_ERROR.getCode(), ApiErrorCode.JOB_PARAM_FORMAT_ERROR.getDesc());
        }
        if (StringUtils.hasText(dto.getJobHeader()) && !JsonUtils.isJsonValid(dto.getJobHeader())) {
            throw new BusinessException(ApiErrorCode.JOB_HEADER_FORMAT_ERROR.getCode(), ApiErrorCode.JOB_HEADER_FORMAT_ERROR.getDesc());
        }

        TJobInfo jobInfo = BeanConvertUtils.convertTo(dto, TJobInfo::new);
        jobInfo.setStatus(JobStatusEnum.PAUSE.getValue());
        jobInfo.setCreatedBy((String) AuthUtils.getLoginId());
        jobInfo.setDelFlag(GlobalConstant.NOT_DELETED);

        int rows = jobInfoMapper.insert(jobInfo);
        if (rows > 0) {
            ScheduleUtils.createScheduleJob(scheduler, jobInfo);
        }

        return rows;
    }


    /**
     * 修改任务
     *
     * @param dto 任务id
     * @return
     */
    @Transactional
    public int edit(JobInfoEditDto dto) throws SchedulerException, TaskException {
        if (dto.getJobTrigger().equals(JobTriggerEnum.CRON.getCode())) {
            if (!StringUtils.hasText(dto.getCronExpression()) || !CronExpression.isValidExpression(dto.getCronExpression())) {
                throw new BusinessException(ApiErrorCode.CRON_EXPRESSION_ERROR.getCode(), ApiErrorCode.CRON_EXPRESSION_ERROR.getDesc());
            }
            dto.setIntervalSeconds(null);
        } else {
            if (dto.getIntervalSeconds() == null || dto.getIntervalSeconds() == 0) {
                throw new BusinessException(ApiErrorCode.JOB_INTERVAL_SECONDS_ERROR.getCode(), ApiErrorCode.JOB_INTERVAL_SECONDS_ERROR.getDesc());
            }
            dto.setCronExpression(null);
        }
        if (StringUtils.hasText(dto.getJobParam()) && !JsonUtils.isJsonValid(dto.getJobParam())) {
            throw new BusinessException(ApiErrorCode.JOB_PARAM_FORMAT_ERROR.getCode(), ApiErrorCode.JOB_PARAM_FORMAT_ERROR.getDesc());
        }
        if (StringUtils.hasText(dto.getJobHeader()) && !JsonUtils.isJsonValid(dto.getJobHeader())) {
            throw new BusinessException(ApiErrorCode.JOB_HEADER_FORMAT_ERROR.getCode(), ApiErrorCode.JOB_HEADER_FORMAT_ERROR.getDesc());
        }

        TJobInfo jobInfo = BeanConvertUtils.convertTo(dto, TJobInfo::new);
        jobInfo.setUpdatedBy((String) AuthUtils.getLoginId());
        int rows = jobInfoMapper.updateById(jobInfo);
        if (rows > 0) {
            jobInfo = jobInfoMapper.selectById(jobInfo.getId());
            ScheduleUtils.updateSchedulerJob(scheduler, jobInfo);
        }
        return rows;
    }


    /**
     * 更新下次执行时间
     *
     * @param jobInfo 任务信息
     * @return int
     */
    public int updateNextExecuteTime(TJobInfo jobInfo) {
        Long id = jobInfo.getId();
        String cronExpression = jobInfo.getCronExpression();
        String jobTrigger = jobInfo.getJobTrigger();
        Integer intervalSeconds = jobInfo.getIntervalSeconds();

        LambdaUpdateWrapper<TJobInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(TJobInfo::getId, id);
        if (jobTrigger.equals(JobTriggerEnum.CRON.getCode())) {
            Date nextExecuteTime = CronUtils.getNextExecution(cronExpression);
            wrapper.set(TJobInfo::getNextExecuteTime, nextExecuteTime);
        } else {
            // 获取当前时间
            Date currentDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.SECOND, intervalSeconds);
            Date nextExecuteTime = calendar.getTime();
            wrapper.set(TJobInfo::getNextExecuteTime, nextExecuteTime);
        }
        return jobInfoMapper.update(null, wrapper);
    }
}
