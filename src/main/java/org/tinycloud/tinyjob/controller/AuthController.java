package org.tinycloud.tinyjob.controller;

import org.tinycloud.security.provider.AuthProvider;
import org.tinycloud.security.util.AuthUtil;
import org.tinycloud.tinyjob.bean.dto.AuthLoginDto;
import org.tinycloud.tinyjob.model.ApiResult;
import org.tinycloud.tinyjob.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 * 系统会话控制器
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-06-01 15:00
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    final static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthProvider authProvider;

    /**
     * 登录
     */
    @PostMapping("/login")
    public ApiResult<?> login(@Validated @RequestBody AuthLoginDto authLoginDto) {
        // 进行用户名和密码校验
        authService.authentication(authLoginDto);
        // 把username作为loginId，执行会话创建操作
        String token = authProvider.login(authLoginDto.getUsername());
        return ApiResult.success(token, "登录成功，欢迎回来！");
    }


    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public ApiResult<?> logout(HttpServletRequest request) {
        authProvider.logout(request);
        return ApiResult.success("退出登录成功！");
    }


    /**
     * 会话校验
     */
    @GetMapping("/getUsername")
    public ApiResult<?> getUsername() {
        return ApiResult.success(AuthUtil.getLoginId(), "获取成功");
    }


    /**
     * 会话校验
     */
    @GetMapping("/init")
    public ApiResult<?> init() {
        Map<String, Object> initInfo = new HashMap<String, Object>();

        Map<String, String> menuItem1 = new HashMap<>();
        menuItem1.put("title", "项目管理");
        menuItem1.put("href", "page/project.html");
        menuItem1.put("icon", "fa fa-navicon");
        menuItem1.put("target", "_self");

        Map<String, String> menuItem2 = new HashMap<>();
        menuItem2.put("title", "主机管理");
        menuItem2.put("href", "page/hosts.html");
        menuItem2.put("icon", "fa fa-navicon");
        menuItem2.put("target", "_self");

        Map<String, String> menuItem3 = new HashMap<>();
        menuItem3.put("title", "任务管理");
        menuItem3.put("href", "page/jobinfo.html");
        menuItem3.put("icon", "fa fa-navicon");
        menuItem3.put("target", "_self");

        Map<String, String> menuItem4 = new HashMap<>();
        menuItem4.put("title", "任务日志");
        menuItem4.put("href", "page/table.html");
        menuItem4.put("icon", "fa fa-navicon");
        menuItem4.put("target", "_self");

        List<Map<String, String>> menuList = new ArrayList<>();
        menuList.add(menuItem1);
        menuList.add(menuItem2);
        menuList.add(menuItem3);
        menuList.add(menuItem4);

        Map<String, String> homeInfo = new HashMap<>();
        homeInfo.put("title", "首页");
        homeInfo.put("href", "page/welcome-1.html?t=1");

        Map<String, String> logoInfo = new HashMap<>();
        logoInfo.put("title", "任务调度中心");
        logoInfo.put("image", "images/logo.png");
        logoInfo.put("href", "");

        initInfo.put("homeInfo", homeInfo);
        initInfo.put("logoInfo", logoInfo);
        initInfo.put("menuInfo", menuList);

        return ApiResult.success(initInfo, "获取成功");
    }
}
