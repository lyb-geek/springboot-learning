package com.github.lybgeek.chain.test;

import com.github.lybgeek.chain.annotation.EnableChain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableChain(basePackages = "com.github.lybgeek.chain.test.filter")
public class ChainTestApplication {



    public static void main(String[] args) {
        SpringApplication.run(ChainTestApplication.class, args);
    }


}