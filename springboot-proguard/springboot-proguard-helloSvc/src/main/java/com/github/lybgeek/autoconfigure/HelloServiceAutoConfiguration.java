package com.github.lybgeek.autoconfigure;


import com.github.lybgeek.config.LicenseProperties;
import com.github.lybgeek.license.aspect.LicenseCheckAspect;
import com.github.lybgeek.service.HelloService;
import com.github.lybgeek.service.impl.HelloServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LicenseProperties.class)
public class HelloServiceAutoConfiguration {


    @Bean
    public HelloService helloService(){
        return new HelloServiceImpl();
    }

    @Bean
    public LicenseCheckAspect licenseCheckAspect(){
        return new LicenseCheckAspect();
    }
}
