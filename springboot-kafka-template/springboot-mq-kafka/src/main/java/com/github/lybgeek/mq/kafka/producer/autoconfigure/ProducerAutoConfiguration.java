package com.github.lybgeek.mq.kafka.producer.autoconfigure;

import com.github.lybgeek.mq.kafka.producer.handler.KafkaProducerHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;


@Configuration
public class ProducerAutoConfiguration {


     @Bean
     @ConditionalOnMissingBean
     public KafkaProducerHandler kafkaProducerHandler(KafkaTemplate kafkaTemplate){
         return new KafkaProducerHandler(kafkaTemplate);
     }
}
