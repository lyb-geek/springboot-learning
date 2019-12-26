package com.github.lybgeek.excel.entity;

import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelVerifyEntity implements IExcelModel, IExcelDataModel {

  protected String errorMsg;

  protected Integer errorRowNum;

  @Override
  public Integer getRowNum() {

    return errorRowNum;
  }

  @Override
  public void setRowNum(Integer errorRowNum) {
    this.errorRowNum = errorRowNum;
  }

  @Override
  public String getErrorMsg() {

    return errorMsg;
  }

  @Override
  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }
}
