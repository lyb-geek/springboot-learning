package com.github.lybgeek.orm.mybatis.dto;

import com.github.lybgeek.orm.mybatis.model.BookOrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookOrderDTO {

    @NotNull(message = "订单名称不能为空")
    private String orderName;

    private BigDecimal total;

    private String orderNo;

    @NotNull(message = "消费者不能为空")
    private String consumer;

    @NotEmpty(message = "订单项不能为空")
    private List<BookOrderItem> bookOrderItems;

    private Long id;

    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String startDate;

    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String endDate;

}
