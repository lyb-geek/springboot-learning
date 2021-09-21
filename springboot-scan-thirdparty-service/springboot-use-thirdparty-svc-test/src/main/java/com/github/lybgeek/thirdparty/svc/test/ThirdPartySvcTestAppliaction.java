package com.github.lybgeek.thirdparty.svc.test;

import com.gitee.lybgeek.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableHelloSvc
//@EnableThirdPartySvc
public class ThirdPartySvcTestAppliaction implements ApplicationRunner {

    @Autowired
    private HelloService helloService;

    public static void main(String[] args) {
        SpringApplication.run(ThirdPartySvcTestAppliaction.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(helloService.sayHello("zhangsan"));
    }
}
