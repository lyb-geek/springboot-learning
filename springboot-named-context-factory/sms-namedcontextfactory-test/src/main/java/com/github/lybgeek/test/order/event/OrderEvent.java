package com.github.lybgeek.test.order.event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEvent {

    private String orderNo;
    private String userName;
    private String mobile;
    private double price;
    private String orderStatus;
    private String productName;
}
