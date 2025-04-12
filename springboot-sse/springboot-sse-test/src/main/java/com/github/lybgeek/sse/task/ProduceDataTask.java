package com.github.lybgeek.sse.task;

import com.github.lybgeek.sse.service.SsePublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: Linyb
 * @date : 2025/4/4 16:54
 **/
@Component
public class ProduceDataTask {

    @Autowired
    private SsePublisherService ssePublisherService;

    private final AtomicInteger counter = new AtomicInteger(0);


    @Scheduled(fixedRate = 5000)
    public void run(){
        int num = counter.incrementAndGet();
        if (num > 10) {
            ssePublisherService.complete();
        }
        System.out.println("num = " + num);
        ssePublisherService.publishMessage("hello-" + num);


    }
}
