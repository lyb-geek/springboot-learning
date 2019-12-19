package com.github.lybgeek.excel.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorExcelRow {

    private int rowNum;

    private String errorMessage;

    private int sheetNo;

    private String sheetName;

}
