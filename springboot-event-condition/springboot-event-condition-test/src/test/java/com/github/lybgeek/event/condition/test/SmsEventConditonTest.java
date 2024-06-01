package com.github.lybgeek.event.condition.test;


import com.github.lybgeek.event.condition.order.OrderService;
import com.github.lybgeek.event.condition.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SmsEventConditonTest {


    @Autowired
    private UserService userService;


    @Autowired
    private OrderService orderService;


    @Test
    public void testAliyunSms(){
        userService.mockRegister("lybgeek","13800000001");
    }


    @Test
    public void testTencentSms(){
        orderService.mockOrder("lybgeek","13800000002");
    }
}
