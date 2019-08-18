package com.github.lybgeek.orm.mybatis.config;

import com.github.lybgeek.orm.mybatis.interceptor.DateTimeInterceptor;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.addInterceptor(dateTimeInterceptor());
    }


    @Bean
    public DateTimeInterceptor dateTimeInterceptor() {
        return new DateTimeInterceptor();
    }

}
