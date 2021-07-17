package com.github.lybgeek.product.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

/**
 *
 **/
@Data
@Measurement(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    /**
     * tag 为true，代表productName为索引字段
     */
    @Column(name = "productName",tag = true)
    private String productName;

    @Column(name = "time")
    private String time;

    @Column(name = "stock")
    private Integer stock;
}
