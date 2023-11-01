package com.lpb.esb.service.auth.config.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * get Spring application context
 *
 * @author tudv1
 */
@Configuration
public class StaticApplicationContext implements ApplicationContextAware {

    static ApplicationContext applicationContext = null;

    public static ApplicationContext getContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

}
