package com.github.lybgeek.jmh.service;


import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class MockBizService {

    @SneakyThrows
    public String query(long mockBizExceuteTime)  {
        long startTime = System.currentTimeMillis();
        if(mockBizExceuteTime >= 50){
            Thread.sleep(mockBizExceuteTime);
        }else{
            TimeUnit.SECONDS.sleep(mockBizExceuteTime);
        }

        long costTime = System.currentTimeMillis() - startTime;

        return "mockBizService query costTime:" + costTime + "ms";

    }
}
