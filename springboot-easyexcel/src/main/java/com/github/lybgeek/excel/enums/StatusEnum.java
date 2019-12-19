package com.github.lybgeek.excel.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {

  ENABLED(1,"正常"),DISABLED(0,"禁用"),UNKNOWED(2,"未知");

  private final Integer value;

  private final String desc;


  public static StatusEnum getStatusByDesc(String desc){

    for (StatusEnum statusEnum : StatusEnum.values()) {
      if(statusEnum.getDesc().equals(desc)){
        return statusEnum;
      }
    }
    return UNKNOWED;

  }


  public static StatusEnum getStatusByType(Integer value){
    for (StatusEnum statusEnum : StatusEnum.values()) {
      if(statusEnum.getValue() == value){
        return statusEnum;
      }
    }
    return UNKNOWED;
  }

}
