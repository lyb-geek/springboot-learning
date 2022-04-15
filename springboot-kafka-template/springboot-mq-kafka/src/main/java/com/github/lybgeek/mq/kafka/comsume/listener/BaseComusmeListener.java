package com.github.lybgeek.mq.kafka.comsume.listener;

import com.github.lybgeek.mq.kafka.comsume.model.KafkaComsumePayLoad;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;


@Slf4j
public abstract class BaseComusmeListener {

    @KafkaHandler
    public final void receive(@Payload String data, @Header(value = KafkaHeaders.RECEIVED_TOPIC,required = false) String receivedTopic,
                              @Header(value = KafkaHeaders.RECEIVED_MESSAGE_KEY,required = false) String receivedMessageKey, @Header(value = KafkaHeaders.RECEIVED_TIMESTAMP,required = false) long receivedTimestamp, Acknowledgment ack){
        KafkaComsumePayLoad kafkaComsumePayLoad = buildKafkaComsumePayLoad(data,receivedTimestamp,receivedTopic,receivedMessageKey);

        boolean isRepeateConsume = isRepeateConsume(kafkaComsumePayLoad);
        if(isRepeateConsume){
            log.warn("messageKey:【{}】，topic：【{}】存在重复消息数据-->【{}】",receivedMessageKey,receivedTopic,data);
            //手工确认
            ack.acknowledge();
            return;
        }

        if(doBiz(kafkaComsumePayLoad)){
            //手工确认
            ack.acknowledge();
        }


    }

    /**
     * 是否重复消费
     * @param kafkaComsumePayLoad
     * @return
     */
    public abstract boolean isRepeateConsume(KafkaComsumePayLoad kafkaComsumePayLoad);

    /**
     * 业务处理
     * @param kafkaComsumerPayLoad
     */
    public abstract boolean doBiz(KafkaComsumePayLoad kafkaComsumerPayLoad);


    private KafkaComsumePayLoad buildKafkaComsumePayLoad(String data, long receivedTimestamp, String receivedTopic, String receivedMessageKey){
        return KafkaComsumePayLoad.builder()
                .data(data)
                .receivedTimestamp(receivedTimestamp)
                .receivedTopic(receivedTopic)
                .receivedMessageKey(receivedMessageKey)
                .build();
    }
}
