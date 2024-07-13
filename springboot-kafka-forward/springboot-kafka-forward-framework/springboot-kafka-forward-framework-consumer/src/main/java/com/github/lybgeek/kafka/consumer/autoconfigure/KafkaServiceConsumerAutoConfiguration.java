package com.github.lybgeek.kafka.consumer.autoconfigure;

import com.github.lybgeek.kafka.consumer.listener.ReceiveRequestParamsListener;
import com.github.lybgeek.kafka.consumer.property.ConsumerProperty;
import com.github.lybgeek.kafka.consumer.service.HttpTemplate;
import com.github.lybgeek.kafka.consumer.service.impl.DefaultHttpTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;


@Configuration
@EnableConfigurationProperties(ConsumerProperty.class)
@AutoConfigureAfter(KafkaAutoConfiguration.class)
public class KafkaServiceConsumerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public HttpTemplate httpTemplate(){
        return new DefaultHttpTemplate();
    }


    @Bean
    @ConditionalOnMissingBean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(KafkaTemplate kafkaTemplate, ConsumerFactory consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setReplyTemplate(kafkaTemplate);
        return factory;
    }


    @Bean
    @ConditionalOnMissingBean
    public ReceiveRequestParamsListener receiveRequestParamsListener(HttpTemplate httpTemplate,ConsumerProperty consumerProperty){
        return new ReceiveRequestParamsListener(consumerProperty,httpTemplate);
    }





}
