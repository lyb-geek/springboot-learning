package com.github.lybgeek.listener;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.web.context.support.ServletRequestHandledEvent;

@Slf4j
public class RequestLogEventListener {

    @EventListener
    public void listener(ServletRequestHandledEvent event){
        log.info("request client ip :{},request method:{}",event.getClientAddress(),event.getMethod());
       log.info("request url:{},cost time:{} ms",event.getRequestUrl(),event.getProcessingTimeMillis());
       if(event.wasFailure()){
           log.error("request fail,error msg:{}",event.getFailureCause());
       }



    }
}
