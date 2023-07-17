package com.github.lybgeek.sync.test.service;


import com.github.lybgeek.common.core.BaseDataSyncTrigger;
import com.github.lybgeek.redis.constant.RedisConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataService {

    private List<Object> dataList = new CopyOnWriteArrayList<>();

    private final RedisTemplate redisTemplate;

    private final BaseDataSyncTrigger dataSyncTrigger;

    public boolean add(String data){
        try {
            Long count = redisTemplate.opsForList().leftPush(RedisConstant.REDIS_LIST_KEY, data);
            if(count > 0){
                dataSyncTrigger.broadcast(data);
                return true;
            }
        } catch (Exception e) {
           log.error("add fail:" + e.getMessage(),e);
        }

        return false;

    }

    public List<Object> getDataList(){
        return dataList;
    }
}
