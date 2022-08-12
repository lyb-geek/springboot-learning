package com.github.lybgeek.test.controller;


import com.github.lybgeek.advisor.annotation.InjectId;
import com.github.lybgeek.model.Customer;
import com.github.lybgeek.model.Order;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customer")
public class CustomerController {

    @PostMapping("add")
    public Customer add(@RequestBody @InjectId Customer customer){
        return customer;
    }
}
