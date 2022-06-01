package com.github.lybgeek.mq.kafka.comsume.multi.one;

import cn.hutool.core.util.ObjectUtil;
import com.github.lybgeek.mq.kafka.comsume.multi.constant.MultiKafkaConstant;
import com.github.lybgeek.mq.kafka.comsume.multi.properties.MultiKafkaComsumeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;


@Configuration
@EnableConfigurationProperties(MultiKafkaComsumeProperties.class)
public class OneKafkaComsumeAutoConfiguration {

    @Bean(MultiKafkaConstant.KAFKA_LISTENER_CONTAINER_FACTORY_ONE)
    public KafkaListenerContainerFactory oneKafkaListenerContainerFactory(@Autowired @Qualifier("oneKafkaProperties") KafkaProperties oneKafkaProperties, @Autowired @Qualifier("oneConsumerFactory") ConsumerFactory oneConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(oneConsumerFactory);
        factory.setConcurrency(ObjectUtil.isEmpty(oneKafkaProperties.getListener().getConcurrency()) ? Runtime.getRuntime().availableProcessors() : oneKafkaProperties.getListener().getConcurrency());
        factory.getContainerProperties().setAckMode(ObjectUtil.isEmpty(oneKafkaProperties.getListener().getAckMode()) ? ContainerProperties.AckMode.MANUAL:oneKafkaProperties.getListener().getAckMode());
        return factory;
    }

    @Primary
    @Bean
    public ConsumerFactory oneConsumerFactory(@Autowired @Qualifier("oneKafkaProperties") KafkaProperties oneKafkaProperties){
        return new DefaultKafkaConsumerFactory(oneKafkaProperties.buildConsumerProperties());
    }


    @Primary
    @ConfigurationProperties(prefix = "lybgeek.kafka.one")
    @Bean
    public KafkaProperties oneKafkaProperties(){
        return new KafkaProperties();
    }

}
