package com.github.lybgeek.apollo.hystrix.config;

import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置断路器切面，不配置断路器不生效
 * @see HystrixCommandAspect
 */
@Configuration
public class HystrixAspectConfig {


    @Bean
    public HystrixCommandAspect hystrixCommandAspect(){
        return new HystrixCommandAspect();
    }
}
