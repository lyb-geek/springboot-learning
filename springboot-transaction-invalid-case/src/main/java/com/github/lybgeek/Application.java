package com.github.lybgeek;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan(basePackages = "com.github.lybgeek.user.**.dao")
@EnableAspectJAutoProxy(exposeProxy = true)
public class Application {



    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }


}
