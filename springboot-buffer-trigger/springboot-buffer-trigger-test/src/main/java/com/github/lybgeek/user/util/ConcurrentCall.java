package com.github.lybgeek.user.util;


import lombok.SneakyThrows;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentCall {

    private final int threadNum;
    private final CountDownLatch mainDownLatch;
    private final CountDownLatch threadDownLatch = new CountDownLatch(1);


    private ExecutorService executorService;


    public ConcurrentCall(int threadNum){
        this.threadNum = threadNum;
        this.executorService = Executors.newFixedThreadPool(threadNum);
        this.mainDownLatch = new CountDownLatch(threadNum);
    }


    @SneakyThrows
    public <T> void run(Callable<T> callable)  {
            for (int i = 0; i < threadNum; i++) {
                executorService.submit(()->{
                    try {
                        threadDownLatch.await();
                        Object result = callable.call();
                        System.out.println(result);
                        mainDownLatch.countDown();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            threadDownLatch.countDown();
            mainDownLatch.await();

    }


}
