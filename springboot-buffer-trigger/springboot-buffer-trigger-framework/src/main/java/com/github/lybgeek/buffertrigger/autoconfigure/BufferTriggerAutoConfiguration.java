package com.github.lybgeek.buffertrigger.autoconfigure;

import com.github.lybgeek.buffertrigger.builder.BatchConsumerTriggerFactory;
import com.github.lybgeek.buffertrigger.builder.DelegateBatchConsumerTriggerFactory;
import com.github.lybgeek.buffertrigger.property.BufferTriggerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
@EnableConfigurationProperties(BufferTriggerProperties.class)
public class BufferTriggerAutoConfiguration {


    @Bean
    @Primary
    public BatchConsumerTriggerFactory batchConsumerTriggerFactory(BufferTriggerProperties bufferTriggerProperties){
        return new DelegateBatchConsumerTriggerFactory(bufferTriggerProperties);
    }



}
