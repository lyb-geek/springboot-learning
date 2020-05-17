package com.github.lybgeek.apollo.controller;

import com.github.lybgeek.apollo.convert.OrderMapper;
import com.github.lybgeek.apollo.model.Order;
import com.github.lybgeek.apollo.util.SpringContextUtils;
import com.github.lybgeek.apollo.vo.OrderVO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderMapper orderMapper;


    @GetMapping(value="/order")
    public OrderVO getOrder(){
        Order order = (Order) SpringContextUtils.getBean("order");

        if(ObjectUtils.isNotEmpty(order)){
            return orderMapper.convertDO2VO(order);
        }
        return new OrderVO();

    }
}
