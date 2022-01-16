package com.github.lybgeek.hello;


import com.github.lybgeek.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloApplication implements ApplicationRunner {


    @Autowired
    private HelloService helloService;


    public static void main(String[] args) {
        SpringApplication.run(HelloApplication.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(helloService.hello("zhangsan"));
    }
}
