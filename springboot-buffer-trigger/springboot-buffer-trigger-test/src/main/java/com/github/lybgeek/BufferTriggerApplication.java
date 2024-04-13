package com.github.lybgeek;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.github.lybgeek")
public class BufferTriggerApplication{



    public static void main(String[] args) {
        SpringApplication.run(BufferTriggerApplication.class, args);
    }


}
