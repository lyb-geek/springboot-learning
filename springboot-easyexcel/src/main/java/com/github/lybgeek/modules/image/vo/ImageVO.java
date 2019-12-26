package com.github.lybgeek.modules.image.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;

import com.github.lybgeek.excel.entity.ExcelVerifyEntity;
import com.github.lybgeek.modules.image.constants.ImageConstants;
import com.github.lybgeek.validator.group.ExcelGroup;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true)
public class ImageVO extends ExcelVerifyEntity {

  @Excel(name = ImageConstants.IMAGE_NAME)
  @NotBlank(message = "不能为空",groups = ExcelGroup.class)
  private String imageName;


  @Excel(name = ImageConstants.IMAGE,orderNum = "1",type = 2)
  @NotBlank(message = "不能为空")
  private String imagePath;

  @Excel(name = ImageConstants.IMAGE_DESC,orderNum = "2")
  private String imageDesc;

  /**
   * 状态  0：隐藏   1：显示
   */
  @Excel(name = ImageConstants.IMAGE_STATUS, orderNum = "3", replace = { "隐藏_0", "显示_1" },width = 10)
  private Integer status;



}
