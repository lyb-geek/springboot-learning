package com.github.lybgeek.product.service.impl;


import com.github.lybgeek.influxdb.service.impl.InfluxdbServiceImpl;
import com.github.lybgeek.product.entity.Product;
import com.github.lybgeek.product.service.ProductService;
import org.springframework.stereotype.Service;

/**
 *
 **/
@Service
public class ProductServiceImpl extends InfluxdbServiceImpl<Product> implements ProductService {

}
