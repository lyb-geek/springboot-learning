package com.github.lybgeek.modules.image.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDO {

  private Long id;

  private String imageName;

  private String imagePath;

  private String imageDesc;

  private Integer status;

}
