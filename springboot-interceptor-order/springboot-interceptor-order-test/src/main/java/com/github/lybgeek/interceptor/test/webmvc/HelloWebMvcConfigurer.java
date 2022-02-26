package com.github.lybgeek.interceptor.test.webmvc;


import com.github.lybgeek.interceptor.test.interceptor.HelloHandlerInterceptor;
import com.github.lybgeek.interceptor.test.interceptor.OtherHelloHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class HelloWebMvcConfigurer implements WebMvcConfigurer {


    @Autowired
    private HelloHandlerInterceptor helloHandlerInterceptor;

    @Autowired
    private OtherHelloHandlerInterceptor otherHelloHandlerInterceptor;



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(helloHandlerInterceptor).addPathPatterns("/**").order(100);
        registry.addInterceptor(otherHelloHandlerInterceptor).addPathPatterns("/**").order(-1);

    }
}
