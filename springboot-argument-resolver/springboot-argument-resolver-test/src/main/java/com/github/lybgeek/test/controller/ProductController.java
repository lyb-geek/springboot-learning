package com.github.lybgeek.test.controller;

import com.github.lybgeek.model.Product;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("product")
public class ProductController {

    @PostMapping("add")
    public Product add(@RequestBody Product product){
        return product;
    }
}
