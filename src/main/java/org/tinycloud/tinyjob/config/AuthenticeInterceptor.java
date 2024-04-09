package org.tinycloud.tinyjob.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.tinycloud.tinyjob.constant.CommonCode;
import org.tinycloud.tinyjob.constant.GlobalConstant;
import org.tinycloud.tinyjob.exception.BusinessException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-04-09 12:06
 */
@Component
public class AuthenticeInterceptor implements HandlerInterceptor {
    final static Logger logger = LoggerFactory.getLogger(AuthenticeInterceptor.class);

    /*
     * 进入controller层之前拦截请求
     * 返回值：表示是否将当前的请求拦截下来  false：拦截请求，请求别终止。true：请求不被拦截，继续执行
     * Object obj:表示被拦的请求的目标对象（controller中方法）
     */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        Object loginId = request.getSession().getAttribute(GlobalConstant.SESSION_KEY);
        logger.info("AuthenticeInterceptor -- preHandle -- loginId = {}", loginId);
        if (loginId == null) {
            logger.error("AuthenticeInterceptor -- preHandle -- 请求已拦截");
            throw new BusinessException(CommonCode.NOT_LOGGED_IN);
        }
        // 合格不需要拦截，放行
        return true;
    }

    /*
     * 处理请求完成后视图渲染之前的处理操作
     * 通过ModelAndView参数改变显示的视图，或发往视图的方法
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    /*
     * 视图渲染之后的操作
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
    }
}
