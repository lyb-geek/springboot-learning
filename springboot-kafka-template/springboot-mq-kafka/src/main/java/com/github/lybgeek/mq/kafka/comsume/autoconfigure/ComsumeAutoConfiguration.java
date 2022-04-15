package com.github.lybgeek.mq.kafka.comsume.autoconfigure;

import com.github.lybgeek.mq.kafka.comsume.process.LybGeekKafkaListenerAnnotationBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ComsumeAutoConfiguration {


     @Bean
     @ConditionalOnMissingBean
     public LybGeekKafkaListenerAnnotationBeanPostProcessor nisbosKafkaListenerAnnotationBeanPostProcessor(){
         return new LybGeekKafkaListenerAnnotationBeanPostProcessor();
     }
}
