package com.github.lybgeek.orm.mybatis.util;

import com.github.lybgeek.orm.mybatis.dto.OrderItemsDTO;
import com.github.lybgeek.orm.mybatis.model.BookOrderItem;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum  OrderItemUtil {
    INSTANCE;

    public OrderItemsDTO getOrderItems(List<BookOrderItem> bookOrderItems) {
        Map<String, Integer> orderItemCountMap = new HashMap<>();
        BigDecimal total = BigDecimal.ZERO;
        OrderItemsDTO orderItemsDTO = new OrderItemsDTO();
        for (BookOrderItem bookOrderItem : bookOrderItems) {
            String key = bookOrderItem.getItemName();
            int count = 1;
            if(orderItemCountMap.containsKey(key)){
                count = orderItemCountMap.get(key) + count;
            }
            orderItemCountMap.put(key,count);
            total = total.add(bookOrderItem.getPrice());
        }

        orderItemsDTO.setItemContMap(orderItemCountMap);
        orderItemsDTO.setTotalPrice(total);
        return orderItemsDTO;


    }

}
