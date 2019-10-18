package com.github.lybgeek.common.util;

public class SystemUtil {

  /**
   * 判断是否为window系统
   * @return
   */
  public static boolean isWinOs(){
    String os = System.getProperty("os.name");
    if(os.toLowerCase().startsWith("win")){
       return true;
    }

    return false;

  }

  /**
   * 获取用户当前工作目录
   * @return
   */
  public static String getUserCurrentDir(){
     return System.getProperty("user.dir");
  }

}
