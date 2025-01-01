package com.github.lybgeek.swagger.test.config;


import com.github.lybgeek.swagger.test.user.interceptor.UserHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(new UserHandlerInterceptor()).addPathPatterns("/user/**");
    }
}
