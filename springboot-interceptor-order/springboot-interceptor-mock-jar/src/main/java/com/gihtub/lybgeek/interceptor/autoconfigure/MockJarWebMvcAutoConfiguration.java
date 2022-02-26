package com.gihtub.lybgeek.interceptor.autoconfigure;


import com.gihtub.lybgeek.interceptor.MockJarInterHandlerInterceptor;
import com.gihtub.lybgeek.interceptor.webmvc.MockJarWebMvcConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockJarWebMvcAutoConfiguration {

    @Bean
    public MockJarWebMvcConfigurer mockJarWebMvcConfigurer(){
        MockJarInterHandlerInterceptor mockJarInterHandlerInterceptor = new MockJarInterHandlerInterceptor();
        return new MockJarWebMvcConfigurer(mockJarInterHandlerInterceptor);
    }
}
