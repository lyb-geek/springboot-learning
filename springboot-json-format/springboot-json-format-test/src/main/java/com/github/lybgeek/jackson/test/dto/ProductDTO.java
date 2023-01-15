package com.github.lybgeek.jackson.test.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApiModel(value = "商品管理", description = "商品管理")
@Data
public class ProductDTO implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "商品编码")
    private String stProductCode;

    @ApiModelProperty(value = "生效时间")
    private LocalDateTime dtPropriceStartTime;

    @ApiModelProperty(value = "出厂价（元）")
    //    @JsonProperty(value = "nPropriceFactory")
    private BigDecimal nPropriceFactory;

}
