package com.github.lybgeek.service.impl;


import com.github.lybgeek.service.OomService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OomServiceImpl implements OomService {
    List<Byte[]> list = new ArrayList<>();
    AtomicInteger atomicInteger = new AtomicInteger(0);


    /**
     * vm参数设置： -Xmx100M -Xms100M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=F:\gc.dump
     * @return
     */
    @Override
    public String oom() {
        try {
            while (true) {
                list.add(new Byte[1024 * 1024]);//每次1M
                atomicInteger.incrementAndGet();
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return String.format("添加 %s 次，发生OOM",atomicInteger.get());
    }
}
