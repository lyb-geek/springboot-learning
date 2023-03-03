package com.github.lybgeek.producer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProducerApplication  {


    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class);
    }


}
