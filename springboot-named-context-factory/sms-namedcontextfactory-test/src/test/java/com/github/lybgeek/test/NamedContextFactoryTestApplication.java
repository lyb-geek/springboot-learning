package com.github.lybgeek.test;


import com.github.lybgeek.test.order.OrderService;
import com.github.lybgeek.test.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NamedContextFactoryTestApplication {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;


    @Test
    public void testUserRegister() throws IOException {
        userService.registerUser("lybgeek", "123456","13888888888");
        System.in.read();
    }

    @Test
    public void testOrderCreate() throws IOException {
        orderService.createOrder("lybgeek", "iphone", "13888888888");
        System.in.read();
    }
}
