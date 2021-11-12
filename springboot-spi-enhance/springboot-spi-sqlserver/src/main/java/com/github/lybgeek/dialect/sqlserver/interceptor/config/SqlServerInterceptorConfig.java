package com.github.lybgeek.dialect.sqlserver.interceptor.config;



import com.github.lybgeek.dialect.sqlserver.interceptor.SqlServerDialectInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SqlServerInterceptorConfig {


    @Bean
    public SqlServerDialectInterceptor sqlServerDialectInterceptor(){
        return new SqlServerDialectInterceptor();
    }






}
