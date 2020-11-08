package com.github.lybgeek.chaincontext.feign;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.ILoadBalancer;

import feign.Feign;
import feign.RequestInterceptor;

@ConditionalOnClass(value = { ILoadBalancer.class, Feign.class })
@Configuration
public class FeignConfiguration {
    
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new ChainContextRequestInterceptor();
    }

}
