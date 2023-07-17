package org.tinycloud.tinyjob.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tinycloud.tinyjob.bean.dto.JobLogQueryDto;
import org.tinycloud.tinyjob.bean.vo.JobLogQueryVo;
import org.tinycloud.tinyjob.model.ApiResult;
import org.tinycloud.tinyjob.model.PageModel;
import org.tinycloud.tinyjob.service.JobLogService;

@RestController
@RequestMapping("/joblog")
public class JobLogController {
    final static Logger logger = LoggerFactory.getLogger(JobLogController.class);


    @Autowired
    private JobLogService jobLogService;

    /**
     * 分页查询任务日志
     *
     * @return
     */
    @PostMapping("/query")
    public ApiResult<PageModel<JobLogQueryVo>> query(@RequestBody JobLogQueryDto dto) {
        return ApiResult.success(jobLogService.query(dto), "查询成功!");
    }

}
