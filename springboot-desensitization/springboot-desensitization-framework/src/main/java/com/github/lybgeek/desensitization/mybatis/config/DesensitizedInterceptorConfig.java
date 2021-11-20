package com.github.lybgeek.desensitization.mybatis.config;


import com.github.lybgeek.desensitization.autoconfigure.condition.ConditionalOnDesensitizedByMybatis;
import com.github.lybgeek.desensitization.mybatis.plugin.DesensitizedInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.github.lybgeek.desensitization.property.DesensitizedProperties.*;

@Configuration
@ConditionalOnProperty(prefix = PREFIX,name = ENABLED,havingValue = "true",matchIfMissing = true)
public class DesensitizedInterceptorConfig {


    @Bean
    @ConditionalOnDesensitizedByMybatis
    @ConditionalOnClass({SqlSessionFactory.class, Interceptor.class})
    public DesensitizedInterceptor desensitizedInterceptor(){

        return new DesensitizedInterceptor();
    }
}
