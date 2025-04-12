package com.github.lybgeek.sse.autoconfigure;


import com.github.lybgeek.sse.properties.SsePublisherProperties;
import com.github.lybgeek.sse.service.SsePublisherService;
import com.github.lybgeek.sse.service.support.DirectProcessorSsePublisherService;
import com.github.lybgeek.sse.service.support.ReplayProcessorSsePublisherService;
import com.github.lybgeek.sse.service.support.UnicastProcessorSsePublisherService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.naming.Name;

@Configuration
@EnableConfigurationProperties(SsePublisherProperties.class)
public class SsePublisherAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = SsePublisherProperties.PREIFX, name = "type",havingValue = "direct", matchIfMissing = true)
    public SsePublisherService directProcessorSsePublisherService(){
        return new DirectProcessorSsePublisherService();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = SsePublisherProperties.PREIFX, name = "type",havingValue = "unicast")
    public SsePublisherService unicastProcessorSsePublisherService(){
        return new UnicastProcessorSsePublisherService();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = SsePublisherProperties.PREIFX, name = "type",havingValue = "replay")
    public SsePublisherService replayProcessorSsePublisherService(){
        return new ReplayProcessorSsePublisherService();
    }
}
