package com.github.lybgeek.mq.kafka.comsume.multi.two;

import cn.hutool.core.util.ObjectUtil;

import com.github.lybgeek.mq.kafka.comsume.multi.condition.ConditionalOnMultiKafkaComsumeEnabled;
import com.github.lybgeek.mq.kafka.comsume.multi.constant.MultiKafkaConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;


@Configuration
@ConditionalOnMultiKafkaComsumeEnabled
public class TwoKafkaComsumeAutoConfiguration {

    @Bean(MultiKafkaConstant.KAFKA_LISTENER_CONTAINER_FACTORY_TWO)
    public KafkaListenerContainerFactory twoKafkaListenerContainerFactory(@Autowired @Qualifier("twoKafkaProperties") KafkaProperties twoKafkaProperties, @Autowired @Qualifier("twoConsumerFactory") ConsumerFactory twoConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(twoConsumerFactory);
        factory.setConcurrency(ObjectUtil.isEmpty(twoKafkaProperties.getListener().getConcurrency()) ? Runtime.getRuntime().availableProcessors() : twoKafkaProperties.getListener().getConcurrency());
        factory.getContainerProperties().setAckMode(ObjectUtil.isEmpty(twoKafkaProperties.getListener().getAckMode()) ? ContainerProperties.AckMode.MANUAL:twoKafkaProperties.getListener().getAckMode());

        return factory;
    }

    @Bean
    public ConsumerFactory twoConsumerFactory(@Autowired @Qualifier("twoKafkaProperties") KafkaProperties twoKafkaProperties){

        return new DefaultKafkaConsumerFactory(twoKafkaProperties.buildConsumerProperties());
    }



    @ConfigurationProperties(prefix = "lybgeek.kafka.two")
    @Bean
    public KafkaProperties twoKafkaProperties(){
        return new KafkaProperties();
    }


}
