package com.github.lybgeek.bean.test.autoconfigure;


import com.github.lybgeek.bean.test.service.EchoService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ThirdPartyEchoServiceAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public EchoService echoService(){
        System.out.println(">>>>>> 我是第三方组件BEAN：EchoService");
        return new EchoService("第三方组件服务");
    }
}
