package com.stackroute;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;


public class SpringApplicationContext implements ApplicationContextAware {
    private static ApplicationContext CONTEXT;


    public static Object getBean(String beanName) {
        return CONTEXT.getBean(beanName);
    }

    @Override
    public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext) throws BeansException {
        CONTEXT = applicationContext;
    }

}