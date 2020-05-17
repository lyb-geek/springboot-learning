package com.github.lybgeek.apollo.controller;

import com.github.lybgeek.apollo.convert.ProductMapper;
import com.github.lybgeek.apollo.model.Product;
import com.github.lybgeek.apollo.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrdouctController {

    @Autowired
    private Product product;

    @Autowired
    private ProductMapper productMapper;

    @GetMapping(value = "/product")
    public ProductVO getProduct(){
        return productMapper.convertDO2VO(product);
    }
}
