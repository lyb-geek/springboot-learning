package com.github.lybgeek.echo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Pf4jApplication implements ApplicationRunner {



    public static void main(String[] args) {
        SpringApplication.run(Pf4jApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {


    }
}
