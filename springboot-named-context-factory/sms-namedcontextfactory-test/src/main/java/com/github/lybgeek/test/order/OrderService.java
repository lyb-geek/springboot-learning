package com.github.lybgeek.test.order;


import com.github.lybgeek.test.order.event.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ApplicationContext applicationContext;

    public static final String SERVICE_NAME = "orderService";

    public void createOrder(String userName, String productName,String mobile) {
        System.out.println("用户"+userName+"下单成功");
        OrderEvent orderEvent = OrderEvent.builder()
                .userName(userName).productName(productName)
                .orderStatus("待支付")
                .orderNo(UUID.randomUUID().toString())
                .price(10)
                .mobile(mobile).build();
        applicationContext.publishEvent(orderEvent);

    }
}
