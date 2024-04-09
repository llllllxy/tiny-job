package org.tinycloud.tinyjob.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.tinycloud.tinyjob.constant.GlobalConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-04-09 11:59
 */
public class AuthUtils {

    /**
     * 获取当前请求对象
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = null;
        try {
            request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            return request;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前响应对象
     *
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        HttpServletResponse response = null;
        try {
            response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    public static Object getLoginId() {
        Object loginId = getRequest().getSession().getAttribute(GlobalConstant.SESSION_KEY);
        return loginId;
    }
}
