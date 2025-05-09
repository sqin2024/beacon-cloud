package com.sqin.gateway.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author Qin
 * @Date 2025/5/7 20:05
 * @Description
 **/
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }

    public static Object getBeanByName(String beanName) {
        return SpringUtil.applicationContext.getBean(beanName);
    }

    public static <T> T getBeanByClass(Class<T> clazz) {
        return SpringUtil.applicationContext.getBean(clazz);
    }
}
