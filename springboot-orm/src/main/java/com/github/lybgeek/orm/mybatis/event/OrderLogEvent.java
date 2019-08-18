package com.github.lybgeek.orm.mybatis.event;

import com.alibaba.fastjson.JSON;
import com.github.lybgeek.orm.jpa.model.OrderLog;
import com.github.lybgeek.orm.jpa.service.OrderLogService;
import com.github.lybgeek.orm.mybatis.model.BookOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderLogEvent {

    @Autowired
    private OrderLogService orderLogService;

    @EventListener
    @Async
    public void saveOrderLog(BookOrder bookOrder){
        String json = JSON.toJSONString(bookOrder);
        log.info("记录订单日志");
        OrderLog orderLog = OrderLog.builder().orderContent(json).orderId(bookOrder.getId()).orderName(bookOrder.getOrderName()).build();
        orderLogService.saveLog(orderLog);
    }
}
