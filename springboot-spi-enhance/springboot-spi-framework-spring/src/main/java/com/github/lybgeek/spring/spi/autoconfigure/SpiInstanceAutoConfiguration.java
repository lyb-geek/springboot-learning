package com.github.lybgeek.spring.spi.autoconfigure;


import com.github.lybgeek.spring.interceptor.autoconfigure.InterceptorAutoConfiguration;
import com.github.lybgeek.spring.interceptor.handler.InterceptorHandler;
import com.github.lybgeek.spring.spi.factory.SpiAutowiredBeanFactoryPostProcessor;
import com.github.lybgeek.spring.spi.factory.SpiInstanceInitializingSingleton;
import com.github.lybgeek.spring.spi.factory.SpiInstancePostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(InterceptorAutoConfiguration.class)
public class SpiInstanceAutoConfiguration {

//    @Bean
    public SpiInstancePostProcessor spiInstancePostProcessor(final InterceptorHandler interceptorHandler,final DefaultListableBeanFactory beanFactory){
       return new SpiInstancePostProcessor(interceptorHandler,beanFactory);
    }

    @Bean
    public SpiInstanceInitializingSingleton spiInstancePostProcessor(final InterceptorHandler interceptorHandler){
        return new SpiInstanceInitializingSingleton(interceptorHandler);
    }

    @Bean
    public SpiAutowiredBeanFactoryPostProcessor spiAutowiredBeanFactoryPostProcessor(){
        return new SpiAutowiredBeanFactoryPostProcessor();
    }



 




}
