package com.github.lybgeek.apollo.convert;

import com.github.lybgeek.apollo.model.Order;
import com.github.lybgeek.apollo.vo.OrderVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderVO convertDO2VO(Order order);
}
