package com.github.lybgeek.dao;


import com.github.lybgeek.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

@Repository
public class ProductMockDao {
    private LongAdder idIncrService = new LongAdder();

    private static Map<Long, Product> mockDb = new ConcurrentHashMap<>();

    public Product save(Product product) {
        if(Objects.isNull(product.getId()) || !mockDb.containsKey(product.getId())){
            idIncrService.increment();
            product.setId(idIncrService.longValue());
        }
        mockDb.put(product.getId(),product);

        return product;
    }
}
