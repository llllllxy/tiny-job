package org.tinycloud.tinyjob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tinycloud.tinyjob.model.ApiResult;
import org.tinycloud.tinyjob.service.WelcomeService;

import java.util.Map;

@RestController
@RequestMapping("/welcome")
public class WelcomeController {

    @Autowired
    private WelcomeService welcomeService;

    @GetMapping("/quantity")
    public ApiResult<Map<String, Long>> quantity() {
        return ApiResult.success(welcomeService.quantity(), "查询成功!");
    }


    @GetMapping("/chartsInfo")
    public ApiResult<Map<String, Object>> chartsInfo() {
        return ApiResult.success(welcomeService.chartsInfo(), "查询成功!");
    }
}
