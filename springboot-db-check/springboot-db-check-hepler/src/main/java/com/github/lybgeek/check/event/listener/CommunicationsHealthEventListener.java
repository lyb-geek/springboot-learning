package com.github.lybgeek.check.event.listener;


import cn.hutool.extra.spring.SpringUtil;

import com.github.lybgeek.check.callback.DBCommunicationsCallBack;
import com.github.lybgeek.check.event.CommunicationsHealthEvent;
import org.springframework.context.event.EventListener;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class CommunicationsHealthEventListener {

    private final ExecutorService executorService = Executors.newFixedThreadPool(10, new ThreadFactory() {
        private AtomicInteger atomicInteger = new AtomicInteger();
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r,"conn-health-pool-" + atomicInteger.getAndIncrement());
        }
    });

    @EventListener
    public void listener(CommunicationsHealthEvent communicationsHealthEvent){
        Map<String, DBCommunicationsCallBack> communicationsCallBackMap = SpringUtil.getBeansOfType(DBCommunicationsCallBack.class);
        for (DBCommunicationsCallBack callBack : communicationsCallBackMap.values()) {
            executorService.execute(()->{
                callBack.executeIfCommunicationsHealth(communicationsHealthEvent.getConn());
            });
        }
    }
}
