package com.github.lybgeek.orm.mybatis.model;

import com.github.lybgeek.orm.mybatis.annotation.CreateDate;
import com.github.lybgeek.orm.mybatis.annotation.UpdateDate;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true)
public class BookOrder{
    private Long id;

    @CreateDate
    private Date createDate;
    @UpdateDate
    private Date updateDate;

    private String orderName;

    private BigDecimal total;

    private String orderNo;

    private String consumer;

    private List<BookOrderItem> bookOrderItems;

    private transient String startDate;

    private transient String endDate;


}