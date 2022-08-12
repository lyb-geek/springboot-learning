package com.github.lybgeek.test.controller;


import com.github.lybgeek.model.Order;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {

    @PostMapping("add")
    public Order add(@RequestBody Order order){
        return order;
    }
}
