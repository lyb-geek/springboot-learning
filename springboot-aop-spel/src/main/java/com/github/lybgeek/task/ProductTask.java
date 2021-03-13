package com.github.lybgeek.task;


import com.github.lybgeek.entity.Product;
import com.github.lybgeek.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ProductTask {

    @Autowired
    private ProductService productService;

    private AtomicInteger atomicInteger = new AtomicInteger();

    @Scheduled(fixedRate = 5000)
    public void run(){
        int i = atomicInteger.incrementAndGet();
        Product product = Product.builder()
                .productName("product" + i)
                .price(new BigDecimal(i))
                .desc("product desc" + i).build();
        productService.save(product);
    }
}
