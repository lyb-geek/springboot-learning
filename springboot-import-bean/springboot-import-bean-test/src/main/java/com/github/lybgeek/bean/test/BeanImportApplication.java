package com.github.lybgeek.bean.test;


import com.github.lybgeek.bean.annotation.LybGeekEnableAutoConfiguration;
import com.github.lybgeek.bean.test.service.EchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.github.lybgeek.bean.test")
@LybGeekEnableAutoConfiguration
public class BeanImportApplication implements ApplicationRunner {

    @Autowired
    private EchoService echoService;

    public static void main(String[] args) {
        SpringApplication.run(BeanImportApplication.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        echoService.echo("自定义事件驱动");
    }
}
