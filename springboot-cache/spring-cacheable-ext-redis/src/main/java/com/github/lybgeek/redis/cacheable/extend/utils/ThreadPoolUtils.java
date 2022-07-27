package com.github.lybgeek.redis.cacheable.extend.utils;


import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public final class ThreadPoolUtils {

    private static ThreadPoolTaskExecutor taskExecutor = null;

    static {
        taskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数
        taskExecutor.setCorePoolSize(5);
        // 最大线程数
        taskExecutor.setMaxPoolSize(50);
        // 队列最大长度
        taskExecutor.setQueueCapacity(1000);
        taskExecutor.setThreadFactory(new ThreadFactory() {
            private AtomicInteger atomicInteger = new AtomicInteger();
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("cache-pool-" + atomicInteger.getAndIncrement());
                return thread;
            }
        });
        // 线程池维护线程所允许的空闲时间(单位秒)
        taskExecutor.setKeepAliveSeconds(120);
        // 线程池对拒绝任务(无线程可用)的处理策略 ThreadPoolExecutor.CallerRunsPolicy策略 ,调用者的线程会执行该任务,如果执行器已关闭,则丢弃.
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        taskExecutor.initialize();
    }

    public static void execute(Runnable runnable) {
        taskExecutor.execute(runnable);
    }

    public static ThreadPoolTaskExecutor getTaskExecutor() {
        return taskExecutor;
    }
}
