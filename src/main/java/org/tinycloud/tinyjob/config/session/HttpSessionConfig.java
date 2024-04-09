package org.tinycloud.tinyjob.config.session;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

/**
 * <p>
 * 配置spring-session从请求头中获取会话 ID
 * 它默认是从cookie里面获取的
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-04-09 12:14
 */
@Configuration
public class HttpSessionConfig {

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        // 使用 X-Auth-Token 请求头
        return HeaderHttpSessionIdResolver.xAuthToken();
    }
}
