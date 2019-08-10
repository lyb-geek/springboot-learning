package com.github.lybgeek.mongodb.common.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageQuery<T> {

  /**
   * 页码，从1开始
   */
  private Integer pageNum;

  private Integer pageSize;

  private T queryParams;

}
