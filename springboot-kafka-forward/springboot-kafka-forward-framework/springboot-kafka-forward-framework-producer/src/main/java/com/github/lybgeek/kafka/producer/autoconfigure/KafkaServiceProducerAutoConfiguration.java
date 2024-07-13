package com.github.lybgeek.kafka.producer.autoconfigure;

import com.github.lybgeek.kafka.constant.KafkaConstant;
import com.github.lybgeek.kafka.producer.service.KafkaService;
import com.github.lybgeek.kafka.producer.service.impl.KafkaServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;


@Configuration
@AutoConfigureAfter(KafkaAutoConfiguration.class)
public class KafkaServiceProducerAutoConfiguration {

    /**
     * 创建一个repliesContainer
     * @param containerFactory
     * @return
     */
    @Bean
    public ConcurrentMessageListenerContainer<String, String> repliesContainer(
            ConcurrentKafkaListenerContainerFactory<String, String> containerFactory) {
        // 和RecordHeader中的topic对应起来
        ConcurrentMessageListenerContainer<String, String> repliesContainer =
                containerFactory.createContainer(KafkaConstant.REPLY_TOPIC);
        repliesContainer.getContainerProperties().setGroupId("repliesGroup");
        repliesContainer.setAutoStartup(false);
        return repliesContainer;
    }

    /**
     * 创建一个replyingTemplate
     * @param pf
     * @param repliesContainer
     * @return
     */
    @Bean
    public ReplyingKafkaTemplate<String, String, String> replyingTemplate(
            ProducerFactory<String, String> pf,
            ConcurrentMessageListenerContainer<String, String> repliesContainer) {
        ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate = new ReplyingKafkaTemplate<>(pf, repliesContainer);
       // 设置响应超时为10秒，默认5秒
        replyingKafkaTemplate.setReplyTimeout(10000);
        return replyingKafkaTemplate;

    }




    @Bean
    @ConditionalOnMissingBean
    public KafkaService kafkaService(ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate){
        return new KafkaServiceImpl(replyingKafkaTemplate);
    }



}
