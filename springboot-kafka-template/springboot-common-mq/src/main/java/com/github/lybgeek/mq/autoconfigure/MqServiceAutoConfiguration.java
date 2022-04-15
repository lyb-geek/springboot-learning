package com.github.lybgeek.mq.autoconfigure;

import com.github.lybgeek.mq.service.MqService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MqServiceAutoConfiguration {


     @Bean
     @ConditionalOnMissingBean
     public MqService mqService(ApplicationEventPublisher applicationEventPublisher){
         return new MqService(applicationEventPublisher);
     }
}
