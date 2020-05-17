package com.github.lybgeek.apollo.convert;

import com.github.lybgeek.apollo.model.Product;
import com.github.lybgeek.apollo.vo.ProductVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductVO convertDO2VO(Product product);
}
