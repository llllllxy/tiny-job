package org.tinycloud.tinyjob.controller;


import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.tinycloud.tinyjob.bean.dto.JobInfoAddDto;
import org.tinycloud.tinyjob.exception.TaskException;
import org.tinycloud.tinyjob.model.ApiResult;
import org.tinycloud.tinyjob.service.JobInfoService;

@RestController
@RequestMapping("/jobinfo")
public class JobInfoController {
    final static Logger logger = LoggerFactory.getLogger(JobInfoController.class);

    @Autowired
    private JobInfoService jobInfoService;



    /**
     * 新增任务
     * @return
     */
    @PostMapping("/add")
    public ApiResult<Object> add(@Validated @RequestBody JobInfoAddDto dto) throws SchedulerException, TaskException {
        int num = jobInfoService.add(dto);
        if (num > 0) {
            return ApiResult.success(null, "新增成功!" );
        } else {
            return ApiResult.fail("新增失败，请联系后台管理员!" );
        }
    }


    /**
     * 启动任务
     * @return
     */
    @GetMapping("/start")
    public ApiResult<Object> start(@RequestParam("id") Long id) throws SchedulerException, TaskException {
        int num = jobInfoService.start(id);
        if (num > 0) {
            return ApiResult.success(null, "启动成功!" );
        } else {
            return ApiResult.fail("启动失败，请联系后台管理员!" );
        }
    }


    /**
     * 停止任务
     * @return
     */
    @GetMapping("/stop")
    public ApiResult<Object> stop(@RequestParam("id") Long id) throws SchedulerException, TaskException {
        int num = jobInfoService.stop(id);
        if (num > 0) {
            return ApiResult.success(null, "停止成功!" );
        } else {
            return ApiResult.fail("停止失败，请联系后台管理员!" );
        }
    }


    /**
     * 执行一次任务
     * @return
     */
    @GetMapping("/executeonce")
    public ApiResult<?> executeonce(@RequestParam("id") Long id) throws SchedulerException, TaskException {
        jobInfoService.executeonce(id);
        return ApiResult.success(null, "执行一次成功！");
    }


    /**
     * 删除任务
     * @return
     */
    @GetMapping("/delete")
    public ApiResult<?> delete(@RequestParam("id") Long id) throws SchedulerException, TaskException {
        int  num = jobInfoService.delete(id);
        if (num > 0) {
            return ApiResult.success(null, "删除成功!" );
        } else {
            return ApiResult.fail("删除失败，请联系后台管理员!" );
        }
    }

}
