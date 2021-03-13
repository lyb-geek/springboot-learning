package com.github.lybgeek.cache.event;


import com.github.lybgeek.cache.LocalCache;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CacheContextEvent implements ApplicationListener<ContextRefreshedEvent> {

    private AtomicInteger atomicInteger = new AtomicInteger();

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10, runnable -> {
        Thread thread = new Thread(runnable);
        thread.setName("cache-pool-"+atomicInteger.getAndIncrement());
        return thread;
    });

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        executorService.scheduleWithFixedDelay((Runnable) () -> {
            LocalCache.INSTANCE.printCacheInfo();
        },5,5, TimeUnit.SECONDS);
    }
}
