package com.github.lybgeek.sync.test.task;


import cn.hutool.core.collection.CollectionUtil;
import com.github.lybgeek.redis.constant.RedisConstant;
import com.github.lybgeek.sync.test.service.DataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitTask implements CommandLineRunner {

    private final RedisTemplate redisTemplate;

    private final DataService dataService;


    @Override
    public void run(String... args) throws Exception {
        List redisDataList = redisTemplate.opsForList().range(RedisConstant.REDIS_LIST_KEY, 0, -1);
        if(CollectionUtil.isNotEmpty(redisDataList)){
            dataService.getDataList().addAll(redisDataList);
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Loaded data from redis finished!!!");
        }

    }





}
