package com.lpb.esb.service.gateway2.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * get Spring application context
 *
 * @author tatsuya
 */
@Configuration
public class StaticApplicationContext implements ApplicationContextAware {

    static ApplicationContext applicationContext = null;

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static ApplicationContext getContext() {
        return applicationContext;
    }

}
