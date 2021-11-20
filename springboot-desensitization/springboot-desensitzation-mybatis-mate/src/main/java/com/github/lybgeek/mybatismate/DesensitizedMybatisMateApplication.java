package com.github.lybgeek.mybatismate;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.github.lybgeek.mybatismate.**.dao")
public class DesensitizedMybatisMateApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesensitizedMybatisMateApplication.class);
    }
}
