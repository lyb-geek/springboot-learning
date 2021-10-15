package com.github.lybgeek.dialect.oracle.interceptor.config;


import com.github.lybgeek.dialect.oracle.interceptor.OracleDialectInterceptor;
import com.github.lybgeek.dialect.oracle.interceptor.SpringOracleDialectInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OracleInterceptorConfig {


    @Bean
    public OracleDialectInterceptor oralceDialectInterceptor(){
        return new OracleDialectInterceptor();
    }

    @Bean
    public SpringOracleDialectInterceptor springOralceDialectInterceptor(){
        return new SpringOracleDialectInterceptor();
    }



}
