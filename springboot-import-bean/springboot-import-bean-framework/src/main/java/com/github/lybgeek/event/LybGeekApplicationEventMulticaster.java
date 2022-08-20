package com.github.lybgeek.event;


import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.ResolvableType;

public class LybGeekApplicationEventMulticaster implements ApplicationEventMulticaster {

    private SimpleApplicationEventMulticaster simpleApplicationEventMulticaster;

    public LybGeekApplicationEventMulticaster(SimpleApplicationEventMulticaster simpleApplicationEventMulticaster) {
        this.simpleApplicationEventMulticaster = simpleApplicationEventMulticaster;
    }

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        simpleApplicationEventMulticaster.addApplicationListener(listener);
    }

    @Override
    public void addApplicationListenerBean(String listenerBeanName) {
        simpleApplicationEventMulticaster.addApplicationListenerBean(listenerBeanName);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        simpleApplicationEventMulticaster.removeApplicationListener(listener);

    }

    @Override
    public void removeApplicationListenerBean(String listenerBeanName) {
        simpleApplicationEventMulticaster.removeApplicationListenerBean(listenerBeanName);
    }

    @Override
    public void removeAllListeners() {
       simpleApplicationEventMulticaster.removeAllListeners();
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {
        simpleApplicationEventMulticaster.multicastEvent(event);

    }

    @Override
    public void multicastEvent(ApplicationEvent event, ResolvableType eventType) {
          simpleApplicationEventMulticaster.multicastEvent(event,eventType);
    }
}
