package com.github.lybgeek;


import com.dtflys.forest.Forest;
import com.dtflys.forest.http.ForestResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConsumerApplication {

    public static final String GW_URL = "http://localhost:30000/kafka/";

    private final static Integer THREAD_NUM = 2;
    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(THREAD_NUM);
    private static final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUM);
    public static void main(String[] args) throws Exception{
        Future<String> future = executorService.submit(() -> {
            COUNT_DOWN_LATCH.countDown();
            try {
                COUNT_DOWN_LATCH.await();
                return userAdd("lybgeek", 20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });

        Future<String> future2 = executorService.submit(() -> {
            COUNT_DOWN_LATCH.countDown();
            try {
                COUNT_DOWN_LATCH.await();
                return userAdd("lybgeek-abc", 30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });

        System.out.println(">>>>>>>>>>>>>>result:" + future.get());
        System.out.println("==============result:" + future2.get());

    }



    private static String userAdd(String name,Integer age){
        Map<String,Object> params = new HashMap<>();
        params.put("name",name);
        params.put("age",age);

        ForestResponse response = Forest.config().post(GW_URL + "user/add")
                .contentTypeJson()
                .addBody(params)
                .executeAsResponse();

        if(response.isSuccess()){
            return response.getContent();
        }
        return response.getReasonPhrase();
    }
}