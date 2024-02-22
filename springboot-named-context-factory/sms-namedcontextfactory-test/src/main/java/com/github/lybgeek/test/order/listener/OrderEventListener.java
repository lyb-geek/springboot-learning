package com.github.lybgeek.test.order.listener;

import com.github.lybgeek.sms.SmsService;
import com.github.lybgeek.sms.core.named.SmsClientNameContextFactory;
import com.github.lybgeek.test.order.OrderService;
import com.github.lybgeek.test.order.event.OrderEvent;
import com.github.lybgeek.test.user.UserService;
import com.github.lybgeek.test.user.event.UserRegisterEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final SmsClientNameContextFactory smsClientNameContextFactory;


    @EventListener
    @Async
    public void listener(OrderEvent event) {
        SmsService smsService = smsClientNameContextFactory.getSmsService(OrderService.SERVICE_NAME);
        smsService.send(event.getMobile(), "您已下单成功！订单编号为："+event.getOrderNo() +"，商品名称：" + event.getProductName()+",金额为："+ event.getPrice() + "，请尽快支付！");
    }

}
