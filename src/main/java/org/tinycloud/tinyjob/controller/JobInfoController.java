package org.tinycloud.tinyjob.controller;


import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
     * 启动任务
     * @return
     */
    @GetMapping("/start")
    public ApiResult<Object> start(@RequestParam("id") Long id) throws SchedulerException, TaskException {
        int num = jobInfoService.start(id);
        if (num > 0) {
            return ApiResult.success(null, "启动成功!" );
        } else {
            return ApiResult.fail("启动失败，请联系后台管理员！!" );
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

}
