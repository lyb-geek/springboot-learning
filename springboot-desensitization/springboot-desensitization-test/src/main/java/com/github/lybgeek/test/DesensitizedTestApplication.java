package com.github.lybgeek.test;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.github.lybgeek.test.**.dao")
public class DesensitizedTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesensitizedTestApplication.class);
    }
}
