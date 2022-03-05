package com.github.lybgeek.cor.test;


import com.github.lybgeek.cor.test.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CorApplication implements ApplicationRunner {

    @Autowired
    private HelloService helloService;

    public static void main(String[] args) {
        SpringApplication.run(CorApplication.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(helloService.sayHello("lisi"));
    }
}
