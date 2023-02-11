package com.github.lybgeek.aop;

import com.github.lybgeek.aop.service.EchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AopConfigApplication implements ApplicationRunner {

    @Autowired
    private EchoService echoService;

    public static void main(String[] args) {
        SpringApplication.run(AopConfigApplication.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        echoService.echo("AOP CONFIG");
    }
}
