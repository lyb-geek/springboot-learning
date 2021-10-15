package com.github.lybgeek.spi.test.spring.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.github.lybgeek.spi.test.spring.service")
public class HelloServiceConfig {
}
