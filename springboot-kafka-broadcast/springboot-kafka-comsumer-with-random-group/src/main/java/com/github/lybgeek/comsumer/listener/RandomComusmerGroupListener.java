package com.github.lybgeek.comsumer.listener;


import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class RandomComusmerGroupListener implements ApplicationListener<WebServerInitializedEvent> {

    private Integer serverPort;

    @KafkaListener(topics = "${userCache.topic}",groupId =  "${userCache.topic}_group_" + "#{T(java.util.UUID).randomUUID()})")
    public void receive(Acknowledgment ack, String data){
        System.out.println(String.format("serverPort:【%s】,接收到数据：【%s】",serverPort,data));
        ack.acknowledge();
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        serverPort = event.getWebServer().getPort();
    }
}
