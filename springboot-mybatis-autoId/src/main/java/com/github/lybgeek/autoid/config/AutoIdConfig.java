package com.github.lybgeek.autoid.config;

import com.github.lybgeek.autoid.interceptor.AutoIdInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoIdConfig {

    @Bean
    public AutoIdInterceptor autoIdInterceptor(){
        return new AutoIdInterceptor();
    }
}
