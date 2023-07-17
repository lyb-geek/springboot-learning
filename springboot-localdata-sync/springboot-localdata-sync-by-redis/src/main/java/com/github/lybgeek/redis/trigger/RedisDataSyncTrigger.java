package com.github.lybgeek.redis.trigger;


import cn.hutool.json.JSONUtil;
import com.github.lybgeek.common.core.BaseDataSyncTrigger;
import com.github.lybgeek.common.event.DataSyncTriggerEvent;
import com.github.lybgeek.common.model.SyncDataDTO;
import com.github.lybgeek.common.property.DataSyncTriggerProperty;
import com.github.lybgeek.redis.listener.RedisMessageListener;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

import static com.github.lybgeek.redis.constant.RedisConstant.REDIS_CHANNEL_KEY;

@Slf4j
public class RedisDataSyncTrigger extends BaseDataSyncTrigger implements CommandLineRunner {


    private final RedisTemplate redisTemplate;
    

    public RedisDataSyncTrigger(RedisTemplate redisTemplate, DataSyncTriggerProperty dataSyncTriggerProperty) {
        super(dataSyncTriggerProperty);
        this.redisTemplate = redisTemplate;
    }

    @EventListener
    public void listener(DataSyncTriggerEvent dataSyncTriggerEvent){
        SyncDataDTO syncDataDTO = SyncDataDTO.builder()
                .data(dataSyncTriggerEvent.getSource())
                        .timeStamp(System.currentTimeMillis())
                                .build();
        try {
            redisTemplate.convertAndSend(REDIS_CHANNEL_KEY, syncDataDTO);
        } catch (Exception e) {
           log.error("redis publish channel 【" + REDIS_CHANNEL_KEY + "】 fail,cause:" + e.getMessage(),e);
        }
    }


    @Override
    public void run(String... args) throws Exception {
        doSubscribe();
    }

    @SneakyThrows
    private void doSubscribe() {
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        RedisMessageListener redisMessageListener = applicationContext.getBean(RedisMessageListener.class);
        connection.subscribe(redisMessageListener,REDIS_CHANNEL_KEY.getBytes("utf-8"));
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Register listen channel : 【{}】",REDIS_CHANNEL_KEY);
    }
}
