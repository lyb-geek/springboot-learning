package com.github.lybgeek.comsumer.listener;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class ComusmerGroupByIpListener  implements ApplicationListener<WebServerInitializedEvent>{

    @Value("${server.addr}")
    private String serverAddr;

    private Integer serverPort;

    @KafkaListener(topics = "${userCache.topic}",groupId =  "${userCache.topic}_group_" + "${server.addr}" + "_${server.port}")
    public void receive(Acknowledgment ack, String data){
        System.out.println(String.format("serverAddr:【%s】,serverPort:【%s】,接收到数据：【%s】",serverAddr,serverPort,data));
        ack.acknowledge();
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        serverPort = event.getWebServer().getPort();
    }


}
