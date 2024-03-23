package com.github.lybgeek.config;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.github.lybgeek.config")
public class ConfigRefreshTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigRefreshTestApplication.class);
    }
}
