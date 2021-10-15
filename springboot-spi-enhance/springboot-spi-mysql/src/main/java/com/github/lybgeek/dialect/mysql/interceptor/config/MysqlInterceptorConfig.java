package com.github.lybgeek.dialect.mysql.interceptor.config;


import com.github.lybgeek.dialect.mysql.interceptor.MysqlDialectInterceptor;
import com.github.lybgeek.dialect.mysql.interceptor.SpringMysqlDialectInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MysqlInterceptorConfig {


    @Bean
    public MysqlDialectInterceptor mysqlDialectInterceptor(){
        return new MysqlDialectInterceptor();
    }

    @Bean
    public SpringMysqlDialectInterceptor springMysqlDialectInterceptor(){
        return new SpringMysqlDialectInterceptor();
    }





}
