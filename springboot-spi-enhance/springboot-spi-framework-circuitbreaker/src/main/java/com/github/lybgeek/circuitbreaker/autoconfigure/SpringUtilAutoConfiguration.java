package com.github.lybgeek.circuitbreaker.autoconfigure;


import cn.hutool.extra.spring.SpringUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringUtilAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SpringUtil springUtil(){
        return new SpringUtil();
    }
}
