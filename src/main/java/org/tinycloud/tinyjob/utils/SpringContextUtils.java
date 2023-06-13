package org.tinycloud.tinyjob.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring上下文获取工具类
 *
 * @author liuxingyu01
 * @version 2023-02-09 16:11
 */
@Component
public class SpringContextUtils implements ApplicationContextAware, DisposableBean {
    public static ApplicationContext applicationContext;

    private SpringContextUtils() {
        super();
    }

    /**
     * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }



    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return getApplicationContext().getBean(name, requiredType);
    }

    public static boolean containsBean(String name) {
        return getApplicationContext().containsBean(name);
    }

    public static boolean isSingleton(String name) {
        return getApplicationContext().isSingleton(name);
    }

    public static Class<? extends Object> getType(String name) {
        return getApplicationContext().getType(name);
    }


    /**
     * 清除applicationContext静态变量
     */
    public static void cleanApplicationContext() {
        applicationContext = null;
    }

    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext未注入，请检查");
        }
    }

    @Override
    public void destroy() throws Exception {
        SpringContextUtils.cleanApplicationContext();
    }
}
