package com.github.lybgeek.bean.test.service;


import com.github.lybgeek.event.publish.LybGeekEventPublish;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class EchoService {

    @Autowired
    private LybGeekEventPublish lybGeekEventPublish;

    private final String serviceName;

    public EchoService(String serviceName) {
        this.serviceName = serviceName;
    }

    public void echo(String content){
        log.info(">>>>>> 服务：【{}】, 开始发送数据：【{}】",serviceName,content);
        lybGeekEventPublish.publish(String.format("serviceName:【%s】，发送数据：【%s】",serviceName,content));
        log.info(">>>>>> 服务：【{}】, 结束发送数据：【{}】",serviceName,content);
    }
}
