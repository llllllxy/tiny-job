package org.tinycloud.tinyjob.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * WebMvcConfig配置类
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-05-30 13:02
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthenticeInterceptor authenticeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {


        List<String> excludePath = new ArrayList<String>();
        // 开放登录接口
        excludePath.add("/");
        excludePath.add("/auth/login");
        excludePath.add("/auth/getCode");
        // 开放前端静态资源和静态页面
        excludePath.add("/static/**");
        excludePath.add("/api/**");
        excludePath.add("/css/**");
        excludePath.add("/images/**");
        excludePath.add("/js/**");
        excludePath.add("/lib/**");
        excludePath.add("/page/**");
        excludePath.add("/index.html");


        // 注册会话拦截器
        registry.addInterceptor(authenticeInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
    }

    /**
     * 配置静态资源映射
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置静态资源映射
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/lib/**").addResourceLocations("classpath:/static/lib/");
        registry.addResourceHandler("/page/**").addResourceLocations("classpath:/static/page/");
        registry.addResourceHandler("/index.html").addResourceLocations("classpath:/static/");

        // 配置swagger-ui资源映射(knife4j)
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
