package com.github.lybgeek.mq.kafka.comsume.multi.three;

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
public class ThreeKafkaComsumeAutoConfiguration {

    @Bean(MultiKafkaConstant.KAFKA_LISTENER_CONTAINER_FACTORY_THREE)
    public KafkaListenerContainerFactory threeKafkaListenerContainerFactory(@Autowired @Qualifier("threeKafkaProperties") KafkaProperties threeKafkaProperties, @Autowired @Qualifier("threeConsumerFactory") ConsumerFactory threeConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(threeConsumerFactory);
        factory.setConcurrency(ObjectUtil.isEmpty(threeKafkaProperties.getListener().getConcurrency()) ? Runtime.getRuntime().availableProcessors() : threeKafkaProperties.getListener().getConcurrency());
        factory.getContainerProperties().setAckMode(ObjectUtil.isEmpty(threeKafkaProperties.getListener().getAckMode()) ? ContainerProperties.AckMode.MANUAL:threeKafkaProperties.getListener().getAckMode());
        return factory;
    }

    @Bean
    public ConsumerFactory threeConsumerFactory(@Autowired @Qualifier("threeKafkaProperties") KafkaProperties threeKafkaProperties){
        return new DefaultKafkaConsumerFactory(threeKafkaProperties.buildConsumerProperties());
    }


    @ConfigurationProperties(prefix = "lybgeek.kafka.three")
    @Bean
    public KafkaProperties threeKafkaProperties(){
        return new KafkaProperties();
    }

}
