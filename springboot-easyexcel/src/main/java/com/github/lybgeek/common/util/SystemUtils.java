package com.github.lybgeek.common.util;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SystemUtils {

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





  /**
   * 获取excel模板路径
   * @return
   */
 public static String getBaseExcelTemplatePath(){
    return SystemUtils.class.getResource("/").getPath()+ File.separator + "excel-template" + File.separator;
 }

  public static void main(String[] args) {

    System.out.println(getBaseExcelTemplatePath());
  }
}
