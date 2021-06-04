package com.github.lybgeek;


import com.github.lybgeek.feign.annotation.EnableAppendEnv2FeignServiceName;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableAppendEnv2FeignServiceName
@EnableFeignClients(basePackages = "com.github.lybgeek.client")
public class ConsumerApplication {


    public static void main(String[] args) {

        SpringApplication.run(ConsumerApplication.class);

    }


}
