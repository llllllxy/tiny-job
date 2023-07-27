package org.tinycloud.tinyjob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.tinycloud.tinyjob.bean.dto.MailConfigEditDto;
import org.tinycloud.tinyjob.bean.vo.MailConfigVo;
import org.tinycloud.tinyjob.model.ApiResult;
import org.tinycloud.tinyjob.service.MailConfigService;


@RestController
@RequestMapping("/mailconfig")
public class MailConfigController {

    @Autowired
    private MailConfigService mailConfigService;

    @GetMapping("/detail")
    public ApiResult<MailConfigVo> detail() {
        return ApiResult.success(mailConfigService.detail(), "获取成功");
    }

    @PostMapping("/edit")
    public ApiResult<Boolean> edit(@Validated @RequestBody MailConfigEditDto dto) {
        return ApiResult.success(mailConfigService.edit(dto), "修改成功");
    }
}
