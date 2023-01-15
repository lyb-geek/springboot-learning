package com.github.lybgeek.jackson.test.controller;


import com.github.lybgeek.jackson.test.dto.ProductDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("product")
@Api(tags = "商品管理")
public class ProductController {


    @PostMapping("add")
    @ApiOperation(value = "添加商品")
    public ProductDTO add(@RequestBody ProductDTO productDTO){
        productDTO.setId(UUID.randomUUID().toString());
        productDTO.setDtPropriceStartTime(LocalDateTime.now());
        return productDTO;
    }
}
