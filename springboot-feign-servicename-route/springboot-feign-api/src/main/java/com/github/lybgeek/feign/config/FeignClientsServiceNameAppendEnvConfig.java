package com.github.lybgeek.feign.config;


import com.github.lybgeek.feign.process.FeignClientsServiceNameAppendBeanPostProcessor;
import org.springframework.context.annotation.Bean;

public class FeignClientsServiceNameAppendEnvConfig {


    @Bean
    public FeignClientsServiceNameAppendBeanPostProcessor feignClientsServiceNameAppendBeanPostProcessor(){
        return new FeignClientsServiceNameAppendBeanPostProcessor();
    }
}
