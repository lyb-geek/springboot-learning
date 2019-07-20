package com.github.lybgeek.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.util.*;

public class BeanUtil {

  public static void copyNotNUllProperties(Object source, Object target) {
    //获取空属性并处理成null
    String[] nullProperties = getNullProperties(source);
    BeanUtils.copyProperties(source, target, nullProperties);

  }

  /**
   * 获取对象的空属性
   */
  private static String[] getNullProperties(Object src) {
    //1.获取Bean
    BeanWrapper srcBean = new BeanWrapperImpl(src);
    //2.获取Bean的属性描述
    PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
    //3.获取Bean的空属性
    Set<String> properties = new HashSet<>();
    for (PropertyDescriptor propertyDescriptor : pds) {
      String propertyName = propertyDescriptor.getName();
      Object propertyValue = srcBean.getPropertyValue(propertyName);
      if (propertyValue == null) {
        srcBean.setPropertyValue(propertyName, null);
        properties.add(propertyName);
      }
    }
    return properties.toArray(new String[0]);
  }

  public static <T> T copyObj(Object src, Class<T> dstClass) {

    if (src == null) {
      return null;
    }
    T dst;
    try {
      dst = dstClass.newInstance();
      BeanUtils.copyProperties(src, dst);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return dst;
  }

  public static <T> List<T> copyList(List<?> src, Class<T> dstClass) {

    if (CollectionUtils.isEmpty(src)) {
      return Collections.emptyList();
    }
    List dst = new ArrayList<>(src.size());
    for (Object o : src) {
      dst.add(copyObj(o, dstClass));
    }
    return dst;
  }
}
