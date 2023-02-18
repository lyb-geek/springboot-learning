package com.github.lybgeek.sms.test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SmsPluginApplication implements ApplicationRunner {

    @Autowired
    private ApplicationContext applicationContext;


    public static void main(String[] args) {
        SpringApplication.run(SmsPluginApplication.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
//            System.out.println(beanDefinitionName + ":" + applicationContext.getBean(beanDefinitionName));
//        }
    }
}
