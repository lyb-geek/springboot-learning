package com.github.lybgeek.service.impl;


import com.github.lybgeek.cache.annotation.LocalCacheable;
import com.github.lybgeek.dao.ProductMockDao;
import com.github.lybgeek.entity.Product;
import com.github.lybgeek.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {



    @Autowired
    private ProductMockDao productMockDao;

    @Override
    @LocalCacheable(key = "#product.id",condition = "#product.price ge 10")
    public Product save(Product product) {
        return productMockDao.save(product);
    }


}
