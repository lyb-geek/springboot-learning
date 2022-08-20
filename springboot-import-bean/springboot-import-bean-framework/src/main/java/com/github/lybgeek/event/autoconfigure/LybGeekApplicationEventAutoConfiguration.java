package com.github.lybgeek.event.autoconfigure;


import com.github.lybgeek.event.LybGeekApplicationEventMulticaster;
import com.github.lybgeek.event.listener.LybGeekEventListenerMethodProcessor;
import com.github.lybgeek.event.properties.LybGeekApplicationEventProperties;
import com.github.lybgeek.event.publish.LybGeekEventPublish;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;


@Configuration
@EnableConfigurationProperties(LybGeekApplicationEventProperties.class)
public class LybGeekApplicationEventAutoConfiguration{


    @Bean
    @ConditionalOnMissingBean
    public SimpleApplicationEventMulticaster simpleApplicationEventMulticaster(LybGeekApplicationEventProperties lybGeekApplicationEventProperties){
        SimpleApplicationEventMulticaster simpleApplicationEventMulticaster = new SimpleApplicationEventMulticaster();
        if(lybGeekApplicationEventProperties.isAsync()){
            simpleApplicationEventMulticaster.setTaskExecutor(threadPoolTaskExecutor());
        }
        return simpleApplicationEventMulticaster;
    }

    @Bean("threadEventPoolTaskExecutor")
    @ConditionalOnEventAsyncEnabled
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(5);
        threadPoolTaskExecutor.setMaxPoolSize(10);
        threadPoolTaskExecutor.setQueueCapacity(100);
        threadPoolTaskExecutor.setThreadFactory(new ThreadFactory() {
            private AtomicInteger atomicInteger = new AtomicInteger();
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("event-async-" + atomicInteger.getAndIncrement());
                return thread;
            }
        });

        return threadPoolTaskExecutor;

    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(name = "simpleApplicationEventMulticaster")
    public LybGeekApplicationEventMulticaster lybGeekApplicationEventMulticaster(SimpleApplicationEventMulticaster simpleApplicationEventMulticaster){
        return new LybGeekApplicationEventMulticaster(simpleApplicationEventMulticaster);
    }

    @Bean
    @ConditionalOnMissingBean
    public LybGeekEventListenerMethodProcessor lybGeekEventListenerMethodProcessor(){
        return new LybGeekEventListenerMethodProcessor();
    }

    @Bean
    @ConditionalOnMissingBean
    public LybGeekEventPublish lybGeekEventPublish(LybGeekApplicationEventMulticaster lybGeekApplicationEventMulticaster){

        return new LybGeekEventPublish(lybGeekApplicationEventMulticaster);
    }


}
