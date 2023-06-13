package org.tinycloud.tinyjob.controller;

import org.tinycloud.tinyjob.constant.CommonCode;
import org.tinycloud.tinyjob.model.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-05-31 16:33
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/api1")
    public ApiResult<Object> api1() {
        return ApiResult.success(null, "测试成功!" );
    }


    @GetMapping("/api2")
    public ApiResult<?> api2() {
        return ApiResult.fail(CommonCode.NOT_LOGGED_IN);
    }
}
