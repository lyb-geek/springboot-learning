package com.github.lybgeek.mq.service;


import com.github.lybgeek.mq.MqAccess;
import com.github.lybgeek.mq.model.MqReq;
import org.springframework.context.ApplicationEventPublisher;


public class MqService {

    private ApplicationEventPublisher applicationEventPublisher;

    public MqService(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public <T extends MqAccess> void send(T data, String topic){
        MqReq mqReq = new MqReq(topic,data);
        applicationEventPublisher.publishEvent(mqReq);
    }

}
