package com.github.lybgeek.pipeline.spring.test;


import com.github.lybgeek.pipeline.spring.annotation.EnabledPipeline;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnabledPipeline(basePackages = "com.github.lybgeek.pipeline.spring.test")
@ImportResource("classpath:/pipeline.xml")
public class SpringPipelineApplication  {



    public static void main(String[] args) {
        SpringApplication.run(SpringPipelineApplication.class);
    }



}
