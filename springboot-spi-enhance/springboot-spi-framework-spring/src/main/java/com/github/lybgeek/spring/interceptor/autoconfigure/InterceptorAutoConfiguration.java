package com.github.lybgeek.spring.interceptor.autoconfigure;


import com.github.lybgeek.interceptor.Interceptor;
import com.github.lybgeek.interceptor.model.InterceptorChain;
import com.github.lybgeek.spring.interceptor.handler.InterceptorHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
@Slf4j
//@Role(RootBeanDefinition.ROLE_INFRASTRUCTURE)用来解决 is not eligible for getting processed by all BeanPostProcessors
public class InterceptorAutoConfiguration {

    @Bean
    public InterceptorChain interceptorChain(final ObjectProvider<List<Interceptor>> listObjectProvider){
        InterceptorChain chain = new InterceptorChain();
        List<Interceptor> interceptors = listObjectProvider.getIfAvailable(Collections::emptyList);
        log.info("load interceptors-->{}",interceptors);
        for (Interceptor interceptor : interceptors) {
             chain.addInterceptor(interceptor);
        }
        return chain;
    }


    @Bean
    public InterceptorHandler interceptorHandler(final ObjectProvider<List<Interceptor>> listObjectProvider) {
        return new InterceptorHandler(interceptorChain(listObjectProvider));
    }
}
