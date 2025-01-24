package com.github.lybgeek.chain.test.config;


import com.github.lybgeek.chain.delegete.CommandDelegete;
import com.github.lybgeek.chain.test.interceptor.UserHandlerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {


    private final CommandDelegete commandDelegete;




    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserHandlerInterceptor(commandDelegete)).addPathPatterns("/user/**");
    }


}
