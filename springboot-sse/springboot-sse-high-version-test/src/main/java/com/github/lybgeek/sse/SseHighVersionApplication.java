package com.github.lybgeek.sse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SseHighVersionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SseHighVersionApplication.class, args);
    }
}    