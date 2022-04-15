package com.github.lybgeek.mq.handler;

import com.github.lybgeek.mq.model.MqReq;
import com.github.lybgeek.mq.model.MqResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
public abstract class AbstractMqHandler {


    private ExecutorService executorService = Executors.newFixedThreadPool(5, new ThreadFactory() {
        private AtomicInteger atomicInteger = new AtomicInteger();
        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("mq-pool-" + atomicInteger.getAndIncrement());
            return thread;
        }
    });


    public MqResp sendSync(MqReq mqReq){
        log.warn("subclasses no rewrite it");
       return null;
    }

    public abstract void sendAsync(MqReq mqReq);


    @TransactionalEventListener
    public void execute(MqReq mqReq){
        executorService.submit(()->{
            sendAsync(mqReq);
        });
    }

}
