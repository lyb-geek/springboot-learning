package com.github.lybgeek.pf4j.util;

public class StringUtils {

  /**
   * 判断字符串是否为空或仅包含空白字符。
   *
   * @param str 要检查的字符串
   * @return 如果字符串为 null 或者是空字符串或者只包含空白字符，则返回 true；否则返回 false
   */
  public static boolean isBlank(String str) {
    if (str == null || str.trim().isEmpty()) {
      return true;
    }
    return false;
  }

  /**
   * 判断字符串是否不为空且不只包含空白字符。
   *
   * @param str 要检查的字符串
   * @return 如果字符串不为 null 且不是空字符串且不只包含空白字符，则返回 true；否则返回 false
   */
  public static boolean isNotBlank(String str) {
    return !isBlank(str);
  }

  /**
   * 判断字符串是否为空。
   *
   * @param str 要检查的字符串
   * @return 如果字符串为 null 或者是空字符串，则返回 true；否则返回 false
   */
  public static boolean isEmpty(String str) {
    return str == null || str.isEmpty();
  }

  /**
   * 判断字符串是否不为空。
   *
   * @param str 要检查的字符串
   * @return 如果字符串不为 null 且不是空字符串，则返回 true；否则返回 false
   */
  public static boolean isNotEmpty(String str) {
    return !isEmpty(str);
  }
}
