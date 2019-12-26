package com.github.lybgeek.modules.image.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.converters.string.StringImageConverter;
import com.github.lybgeek.modules.image.constants.ImageConstants;
import com.github.lybgeek.modules.image.converter.ImageStatusConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ContentRowHeight(value = 150)
@HeadRowHeight(value = 20)
@ColumnWidth(value = 50)
public class ImageDTO {

  @ExcelProperty(value = ImageConstants.IMAGE_NAME)
  private String imageName;

  @ColumnWidth(value = 100)
  @ExcelProperty(value = ImageConstants.IMAGE,converter = StringImageConverter.class)
  private String imagePath;

  @ExcelProperty(value = ImageConstants.IMAGE_DESC)
  private String imageDesc;

  /**
   * 状态  0：隐藏   1：显示
   */
  @ExcelProperty(value = ImageConstants.IMAGE_STATUS,converter = ImageStatusConverter.class)
  private Integer status;


}
