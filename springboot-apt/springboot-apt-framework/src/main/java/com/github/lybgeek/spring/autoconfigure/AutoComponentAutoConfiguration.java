package com.github.lybgeek.spring.autoconfigure;


import com.github.lybgeek.spring.bean.AutoComponentBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoComponentAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public static AutoComponentBeanPostProcessor autoComponentBeanPostProcessor(){
        return new AutoComponentBeanPostProcessor();
    }
}
