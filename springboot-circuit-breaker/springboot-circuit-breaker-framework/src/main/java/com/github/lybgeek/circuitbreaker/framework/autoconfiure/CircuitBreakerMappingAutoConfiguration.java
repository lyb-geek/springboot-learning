package com.github.lybgeek.circuitbreaker.framework.autoconfiure;


import com.alibaba.cloud.sentinel.SentinelWebAutoConfiguration;
import com.alibaba.cloud.sentinel.custom.SentinelAutoConfiguration;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import com.github.lybgeek.circuitbreaker.framework.ciruitbreaker.aspect.CircuitBreakerAspect;
import com.github.lybgeek.circuitbreaker.framework.ciruitbreaker.callback.BlockExceptionHandler;
import com.github.lybgeek.circuitbreaker.framework.ciruitbreaker.callback.DefaultBlockExceptionHandler;
import com.github.lybgeek.circuitbreaker.framework.ciruitbreaker.origin.QueryStringRequestOriginParser;
import com.github.lybgeek.circuitbreaker.framework.conditon.ConditionalOnCircuitBreaker;
import com.github.lybgeek.circuitbreaker.framework.mapping.CircuitBreakerMappingWebMvcRegistrations;
import com.github.lybgeek.circuitbreaker.framework.properties.CircuitBreakerMappingProperties;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CircuitBreakerMappingProperties.class)
@ConditionalOnCircuitBreaker
@AutoConfigureBefore(value = {SentinelAutoConfiguration.class, SentinelWebAutoConfiguration.class})
public class CircuitBreakerMappingAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public CircuitBreakerMappingWebMvcRegistrations circuitBreakerMappingWebMvcRegistrations(){
        return new CircuitBreakerMappingWebMvcRegistrations();
    }


    @Bean
    @ConditionalOnMissingBean
    public CircuitBreakerAspect circuitBreakerAspect(){

        return new CircuitBreakerAspect();
    }


    @Bean
    @ConditionalOnMissingBean
    public BlockExceptionHandler blockExceptionHandler(){

        return new DefaultBlockExceptionHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestOriginParser requestOriginParser(){
        return new QueryStringRequestOriginParser();
    }


}
