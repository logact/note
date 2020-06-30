package com.cynovan.neptune.oauth.base.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by Aric on 2016/11/8.
 */
@Component
public class SpringContext implements ApplicationContextAware, DisposableBean {

    @Autowired
    private static ApplicationContext applicationContext = null;

    private static Logger logger = LoggerFactory.getLogger(SpringContext.class);

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        // logger.debug("注入ApplicationContext到SpringContextHolder:{}",
        // applicationContext);

        if (SpringContext.applicationContext != null) {
            logger.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:"
                    + SpringContext.applicationContext);
        }

        SpringContext.applicationContext = applicationContext; // NOSONAR
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    public static void clearHolder() {
        applicationContext = null;
    }

    @Override
    public void destroy() throws Exception {
        SpringContext.clearHolder();
    }
}
