package com.github.lybgeek.orm.common.util;

import com.alibaba.druid.filter.config.ConfigTools;

/**
 * druid 数据库密码加密
 * 对密码加密有两种
 * 一种是有如下代码提供
 * 另一种是在cmd命令行中，输入如下命令
 * java -cp druid-1.1.19.jar com.alibaba.druid.filter.config.ConfigTools 你要加密的数据库密码
 */
public class DruidEncryptPwdUtil {

  public static void main(String[] args) {

    try {
      String password = "123456";
      String[] arr = ConfigTools.genKeyPair(512);

      System.out.println("privateKey:" + arr[0]);
      System.out.println("publicKey:" + arr[1]);
      System.out.println("password:" + ConfigTools.encrypt(arr[0], password));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
