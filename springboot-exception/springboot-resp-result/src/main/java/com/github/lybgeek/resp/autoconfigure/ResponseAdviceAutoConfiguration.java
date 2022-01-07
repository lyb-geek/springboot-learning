package com.github.lybgeek.resp.autoconfigure;


import com.github.lybgeek.resp.ResponseAdvice;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackageClasses = ResponseAdvice.class)
public class ResponseAdviceAutoConfiguration {
}
