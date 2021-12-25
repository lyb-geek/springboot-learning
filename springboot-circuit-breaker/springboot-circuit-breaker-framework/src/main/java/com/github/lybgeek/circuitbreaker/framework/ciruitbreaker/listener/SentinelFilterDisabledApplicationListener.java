package com.github.lybgeek.circuitbreaker.framework.ciruitbreaker.listener;


import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.util.StringUtils;

public class SentinelFilterDisabledApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    public static final String SENTINEL_FILTER_ENABLED_KEY = "spring.cloud.sentinel.filter.enabled";
    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {

        String circuitBreakerMappingEnable = event.getEnvironment().getProperty("circuit.breaker.mapping.enabled");

        if(!StringUtils.hasText(circuitBreakerMappingEnable)){
            System.setProperty(SENTINEL_FILTER_ENABLED_KEY,"false");
        }else if("true".equals(circuitBreakerMappingEnable)){
            System.setProperty(SENTINEL_FILTER_ENABLED_KEY,"false");
        }
    }
}
