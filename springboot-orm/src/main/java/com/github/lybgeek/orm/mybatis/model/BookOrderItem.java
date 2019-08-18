package com.github.lybgeek.orm.mybatis.model;

import com.github.lybgeek.orm.mybatis.annotation.CreateDate;
import com.github.lybgeek.orm.mybatis.annotation.UpdateDate;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true)
public class BookOrderItem {

    private Long id;

    @CreateDate
    private Date createDate;
    @UpdateDate
    private Date updateDate;

    private String itemName;

    private BigDecimal price;

    private String detail;

    private Long orderId;




}