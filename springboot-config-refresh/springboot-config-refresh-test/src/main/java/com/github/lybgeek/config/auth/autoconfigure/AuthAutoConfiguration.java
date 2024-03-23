package com.github.lybgeek.config.auth.autoconfigure;

import com.github.lybgeek.config.auth.filter.AuthHandlerInterceptor;
import com.github.lybgeek.config.auth.property.AuthProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableConfigurationProperties(AuthProperty.class)
public class AuthAutoConfiguration implements WebMvcConfigurer {



    @Bean
    @ConditionalOnMissingBean
    public AuthHandlerInterceptor authHandlerInterceptor(){
        return new AuthHandlerInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authHandlerInterceptor()).addPathPatterns("/**");
    }
}
