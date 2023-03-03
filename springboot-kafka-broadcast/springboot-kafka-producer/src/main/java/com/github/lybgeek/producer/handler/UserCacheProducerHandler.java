package com.github.lybgeek.producer.handler;


import com.github.lybgeek.producer.annotation.UserCacheTopic;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
@RequiredArgsConstructor
public class UserCacheProducerHandler {


    private final KafkaTemplate kafkaTemplate;

    @UserCacheTopic
    private String topic;

    @SneakyThrows
    public void send(Object data){
        ListenableFuture listenableFuture = kafkaTemplate.send(topic, data);
        System.out.println(listenableFuture.get());

    }
}
