package com.github.lybgeek.test;


import com.github.lybgeek.test.service.EchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AptApplication implements ApplicationRunner {


    @Autowired
    private EchoService echoService;



    public static void main(String[] args) {
        SpringApplication.run(AptApplication.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        echoService.echo();


    }
}
