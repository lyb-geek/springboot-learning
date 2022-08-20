package com.github.lybgeek.bean.test.autoconfigure;


import com.github.lybgeek.bean.test.service.EchoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EchoServiceAutoConfiguration {


    @Bean
    public EchoService echoService(){
        System.out.println(">>>>>> 我是组件BEAN：EchoService");
        return new EchoService("业务组件服务");
    }
}
