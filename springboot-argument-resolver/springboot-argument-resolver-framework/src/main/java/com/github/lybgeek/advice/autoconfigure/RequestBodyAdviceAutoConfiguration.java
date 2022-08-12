package com.github.lybgeek.advice.autoconfigure;


import com.github.lybgeek.advice.ProductRequestBodyAdvice;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = ProductRequestBodyAdvice.class)
public class RequestBodyAdviceAutoConfiguration {

}
