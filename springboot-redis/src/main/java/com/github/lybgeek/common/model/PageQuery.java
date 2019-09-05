package com.github.lybgeek.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PageQuery<T> {

  private Integer pageNo;

  private Integer pageSize;

  private T queryParams;



}
