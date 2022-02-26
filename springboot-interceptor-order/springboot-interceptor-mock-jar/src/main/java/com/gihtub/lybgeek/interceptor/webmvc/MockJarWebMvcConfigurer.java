package com.gihtub.lybgeek.interceptor.webmvc;


import com.gihtub.lybgeek.interceptor.MockJarInterHandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MockJarWebMvcConfigurer implements WebMvcConfigurer {

    private MockJarInterHandlerInterceptor mockJarInterHandlerInterceptor;

    public MockJarWebMvcConfigurer(MockJarInterHandlerInterceptor mockJarInterHandlerInterceptor) {
        this.mockJarInterHandlerInterceptor = mockJarInterHandlerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(mockJarInterHandlerInterceptor).addPathPatterns("/**");
    }
}
