package com.github.lybgeek.modules.image.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageStatusEnum {

  SHOW(1,"显示"),HIDDEN(0,"隐藏"),UNKNOWED(2,"未知");

  private final Integer value;

  private final String desc;


  public static ImageStatusEnum getStatusByDesc(String desc){

    for (ImageStatusEnum statusEnum : ImageStatusEnum
        .values()) {
      if(statusEnum.getDesc().equals(desc)){
        return statusEnum;
      }
    }
    return UNKNOWED;

  }


  public static ImageStatusEnum getStatusByType(Integer value){
    for (ImageStatusEnum statusEnum : ImageStatusEnum
        .values()) {
      if(statusEnum.getValue() == value){
        return statusEnum;
      }
    }
    return UNKNOWED;
  }

}
