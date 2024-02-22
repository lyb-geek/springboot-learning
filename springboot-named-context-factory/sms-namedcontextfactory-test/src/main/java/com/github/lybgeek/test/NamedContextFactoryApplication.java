package com.github.lybgeek.test;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class NamedContextFactoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(NamedContextFactoryApplication.class, args);
    }
}
