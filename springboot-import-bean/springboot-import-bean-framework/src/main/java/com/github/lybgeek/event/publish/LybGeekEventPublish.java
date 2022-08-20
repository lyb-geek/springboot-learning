package com.github.lybgeek.event.publish;


import com.github.lybgeek.event.LybGeekApplicationEventMulticaster;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.core.ResolvableType;

public class LybGeekEventPublish {

    private LybGeekApplicationEventMulticaster lybGeekApplicationEventMulticaster;

    public LybGeekEventPublish(LybGeekApplicationEventMulticaster lybGeekApplicationEventMulticaster) {
        this.lybGeekApplicationEventMulticaster = lybGeekApplicationEventMulticaster;
    }

    public void publish(Object event){
       if(event instanceof ApplicationEvent){
           ApplicationEvent applicationEvent = (ApplicationEvent) event;
           lybGeekApplicationEventMulticaster.multicastEvent(applicationEvent);
       }else{
           PayloadApplicationEvent applicationEvent = new PayloadApplicationEvent<>(this, event);
           ResolvableType eventType = ((PayloadApplicationEvent<?>) applicationEvent).getResolvableType();
           lybGeekApplicationEventMulticaster.multicastEvent(applicationEvent,eventType);
       }
    }
}
