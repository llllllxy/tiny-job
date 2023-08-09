package org.tinycloud.tinyjob.controller;


import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.tinycloud.tinyjob.bean.dto.JobInfoAddDto;
import org.tinycloud.tinyjob.bean.dto.JobInfoEditDto;
import org.tinycloud.tinyjob.bean.dto.JobInfoQueryDto;
import org.tinycloud.tinyjob.bean.vo.JobInfoQueryVo;
import org.tinycloud.tinyjob.bean.vo.JobInfoSelectVo;
import org.tinycloud.tinyjob.exception.TaskException;
import org.tinycloud.tinyjob.model.ApiResult;
import org.tinycloud.tinyjob.model.PageModel;
import org.tinycloud.tinyjob.service.JobInfoService;
import org.tinycloud.tinyjob.utils.quartz.CronUtils;

import java.util.List;

@RestController
@RequestMapping("/jobinfo")
public class JobInfoController {
    final static Logger logger = LoggerFactory.getLogger(JobInfoController.class);

    @Autowired
    private JobInfoService jobInfoService;


    /**
     * 分页查询任务
     *
     * @return
     */
    @PostMapping("/query")
    public ApiResult<PageModel<JobInfoQueryVo>> query(@RequestBody JobInfoQueryDto dto) {
        return ApiResult.success(jobInfoService.query(dto), "查询成功!");
    }


    /**
     * 新增任务
     *
     * @return
     */
    @PostMapping("/add")
    public ApiResult<Object> add(@Validated @RequestBody JobInfoAddDto dto) throws SchedulerException, TaskException {
        int num = jobInfoService.add(dto);
        if (num > 0) {
            return ApiResult.success(null, "新增成功!");
        } else {
            return ApiResult.fail("新增失败，请联系后台管理员!");
        }
    }


    /**
     * 修改任务
     *
     * @return
     */
    @PostMapping("/edit")
    public ApiResult<Object> edit(@Validated @RequestBody JobInfoEditDto dto) throws SchedulerException, TaskException {
        int num = jobInfoService.edit(dto);
        if (num > 0) {
            return ApiResult.success(null, "修改成功!");
        } else {
            return ApiResult.fail("修改失败，请联系后台管理员!");
        }
    }



    /**
     * 启动任务
     *
     * @return
     */
    @GetMapping("/start")
    public ApiResult<Object> start(@RequestParam("id") Long id) throws SchedulerException, TaskException {
        int num = jobInfoService.start(id);
        if (num > 0) {
            return ApiResult.success(null, "启动成功!");
        } else {
            return ApiResult.fail("启动失败，请联系后台管理员!");
        }
    }


    /**
     * 停止任务
     *
     * @return
     */
    @GetMapping("/stop")
    public ApiResult<Object> stop(@RequestParam("id") Long id) throws SchedulerException, TaskException {
        int num = jobInfoService.stop(id);
        if (num > 0) {
            return ApiResult.success(null, "停止成功!");
        } else {
            return ApiResult.fail("停止失败，请联系后台管理员!");
        }
    }


    /**
     * 执行一次任务
     *
     * @return
     */
    @GetMapping("/executeonce")
    public ApiResult<?> executeonce(@RequestParam("id") Long id) throws SchedulerException, TaskException {
        jobInfoService.executeonce(id);
        return ApiResult.success(null, "执行一次成功！");
    }


    /**
     * 删除任务
     *
     * @return
     */
    @GetMapping("/delete")
    public ApiResult<?> delete(@RequestParam("id") Long id) throws SchedulerException, TaskException {
        int num = jobInfoService.delete(id);
        if (num > 0) {
            return ApiResult.success(null, "删除成功!");
        } else {
            return ApiResult.fail("删除失败，请联系后台管理员!");
        }
    }


    /**
     * 根据主机id获取任务列表(服务于前端下拉框)
     *
     * @return
     */
    @GetMapping("/select")
    public ApiResult<List<JobInfoSelectVo>> select(@RequestParam("hostId") Long hostId) {
        return ApiResult.success(jobInfoService.select(hostId), "获取成功!");
    }


    /**
     * 根据cron表达式来获取未来五次的执行时间
     *
     * @return
     */
    @GetMapping("/getNextExecTime")
    public ApiResult<List<String>> getNextExecTime(@RequestParam("cron") String cron) {
        List<String> list = CronUtils.getNextExecTime(cron, 5);
        return ApiResult.success(list, "获取成功!");
    }
}
