package com.github.lybgeek.excel.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExcelData <T> {

  private List<T> rows;

  private List<ErrorExcelRow> errorRows;

}
