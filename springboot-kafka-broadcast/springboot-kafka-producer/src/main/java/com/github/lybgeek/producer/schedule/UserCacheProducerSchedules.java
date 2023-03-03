package com.github.lybgeek.producer.schedule;


import com.github.lybgeek.producer.handler.UserCacheProducerHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserCacheProducerSchedules {

    private final UserCacheProducerHandler userCacheProducerHandler;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void sche(){
        userCacheProducerHandler.send("hello:" + UUID.randomUUID().toString());
    }
}
