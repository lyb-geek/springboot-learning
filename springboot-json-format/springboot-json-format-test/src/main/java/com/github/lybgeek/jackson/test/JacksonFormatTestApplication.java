package com.github.lybgeek.jackson.test;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.github.lybgeek")
public class JacksonFormatTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(JacksonFormatTestApplication.class);
    }
}
