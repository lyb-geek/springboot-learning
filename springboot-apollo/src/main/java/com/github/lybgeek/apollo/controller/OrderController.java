package com.github.lybgeek.apollo.controller;

import com.github.lybgeek.apollo.annotation.RefreshBean;
import com.github.lybgeek.apollo.convert.OrderMapper;
import com.github.lybgeek.apollo.model.Order;
import com.github.lybgeek.apollo.vo.OrderVO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RefreshBean(refreshFieldBeans = {Order.class})
public class OrderController {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired(required = false)
    private Order order;


    @GetMapping(value="/order")
    public OrderVO getOrder(){
       // order = (Order) SpringContextUtils.getBean("order");

        if(ObjectUtils.isNotEmpty(order)){
            return orderMapper.convertDO2VO(order);
        }
        return new OrderVO();

    }
}
