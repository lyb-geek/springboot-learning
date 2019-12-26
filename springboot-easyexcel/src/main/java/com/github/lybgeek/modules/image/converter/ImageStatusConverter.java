package com.github.lybgeek.modules.image.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.github.lybgeek.excel.enums.StatusEnum;
import com.github.lybgeek.modules.image.enums.ImageStatusEnum;

public class ImageStatusConverter implements Converter<Integer> {


  @Override
  public Class supportJavaTypeKey() {

    return Integer.class;
  }

  @Override
  public CellDataTypeEnum supportExcelTypeKey() {

    return CellDataTypeEnum.STRING;
  }

  @Override
  public Integer convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty,
      GlobalConfiguration globalConfiguration) throws Exception {

    String status = cellData.getStringValue();

    return ImageStatusEnum.getStatusByDesc(status).getValue();
  }

  @Override
  public CellData convertToExcelData(Integer integer, ExcelContentProperty excelContentProperty,
      GlobalConfiguration globalConfiguration) throws Exception {
    String status = ImageStatusEnum.getStatusByType(integer).getDesc();

    return new CellData(status);
  }
}
