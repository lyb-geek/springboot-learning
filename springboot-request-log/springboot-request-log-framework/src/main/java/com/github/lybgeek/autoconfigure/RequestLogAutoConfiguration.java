package com.github.lybgeek.autoconfigure;


import com.github.lybgeek.listener.RequestLogEventListener;
import com.github.lybgeek.repository.CustomInMemoryHttpTraceRepository;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestLogAutoConfiguration {


    @ConditionalOnMissingBean
    @Bean
    @ConditionalOnProperty(prefix = "lybgeek.request-log",name = "enabled",havingValue = "true",matchIfMissing = true)
    public RequestLogEventListener requestLogEventListener(){
        return new RequestLogEventListener();
    }


    @Bean
    @ConditionalOnMissingBean
    public HttpTraceRepository repository(){
    	return new CustomInMemoryHttpTraceRepository();
    }




}
