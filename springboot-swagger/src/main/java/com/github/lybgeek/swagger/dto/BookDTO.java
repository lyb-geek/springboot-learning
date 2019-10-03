package com.github.lybgeek.swagger.dto;

import com.github.structlog4j.IToLog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class BookDTO implements IToLog {
  @ApiModelProperty(value = "编号", name = "id", example = "1")
  private Long id;

  @ApiModelProperty(value = "书名", name = "name", example = "swagger2入门")
  @NotNull(message = "书名不能为空")
  private String bookName;

  @ApiModelProperty(value = "作者", name = "author", example = "张三")
  @NotNull(message = "作者不能为空")
  private String author;

  @ApiModelProperty(value = "描述", name = "description", example = "swagger2入门实战")
  private String description;

  @ApiModelProperty(value = "价格", name = "price", example = "10")
  @NotNull(message = "价格不能为空")
  private BigDecimal price;

  @ApiModelProperty(value = "库存", name = "stock", example = "20")
  @NotNull(message = "库存不能为空")
  private Integer stock;

  @Override
  public Object[] toLog() {
    return new Object[]{
      "id",id,
      "bookName",bookName,
      "author",author,
      "description",description,
      "price",price,
      "stock",stock,

    };
  }
}
