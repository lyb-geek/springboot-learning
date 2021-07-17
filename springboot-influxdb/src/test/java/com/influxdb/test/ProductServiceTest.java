package com.influxdb.test;

import com.github.lybgeek.InfluxdbApplication;

import com.github.lybgeek.influxdb.dto.PageParamsDTO;
import com.github.lybgeek.influxdb.dto.QueryParamsDTO;
import com.github.lybgeek.product.entity.Product;
import com.github.lybgeek.product.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootTest(classes = InfluxdbApplication.class)
@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testSave(){
        Product product = Product.builder().productName("西瓜").stock(30).time(String.valueOf(System.currentTimeMillis())).build();
        boolean isOk = productService.save(product);
        Assert.assertTrue(isOk);
    }

    @Test
    public void testSaveBatch(){
        Product product = Product.builder().productName("橙子").stock(40).time(String.valueOf(System.currentTimeMillis())).build();
        Product product1 = Product.builder().productName("樱桃").stock(50).time(String.valueOf(System.currentTimeMillis())).build();
        List<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product1);
        boolean isOk = productService.saveBatch(products);
        Assert.assertTrue(isOk);
    }


    @Test
    public void testQuery(){
        String sql = "select * from Product";
        List<Product> products = productService.query(sql);
        Assert.assertNotNull(products);
        for (Product product : products) {
            System.out.println(product);
        }
    }

    @Test
    public void testByQueryParams(){
        QueryParamsDTO queryParamsDTO = QueryParamsDTO.builder()
                .fieldKeys(Arrays.asList("*"))
                .queryCondition("productName = '苹果' and stock = '10'").build();
        List<Product> products = productService.query(queryParamsDTO);
        Assert.assertNotNull(products);
        for (Product product : products) {
            System.out.println(product);
        }
    }

    @Test
    public void testPageParams(){
        PageParamsDTO queryParamsDTO = PageParamsDTO.builder().pageNo(1).pageSize(10)
                .fieldKeys(Arrays.asList("*"))
                .queryCondition("productName = '苹果' and stock = '10'").build();
        List<Product> products = productService.query(queryParamsDTO);
        Assert.assertNotNull(products);
        for (Product product : products) {
            System.out.println(product);
        }
    }



}
