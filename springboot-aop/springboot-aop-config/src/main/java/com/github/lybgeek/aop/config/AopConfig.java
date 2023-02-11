package com.github.lybgeek.aop.config;


import com.github.lybgeek.aop.aspect.EchoAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AopConfig {

    @Bean
    public EchoAspect echoAspect(){
        return new EchoAspect();
    }


}
