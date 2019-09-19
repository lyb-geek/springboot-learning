package com.github.lybgeek.common.util;

import com.github.lybgeek.common.elasticsearch.annotation.EsField;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.codehaus.groovy.reflection.CachedClass;
import org.codehaus.groovy.reflection.CachedField;
import org.codehaus.groovy.reflection.ReflectionCache;
import org.reflections.Reflections;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Slf4j
public class ReflectionUtil{


  public static void main(String[] args){
    Set<Class<?>> clzSet = ReflectionUtil.getClasses("com.github.lybgeek.elasticsearch.model",
        Document.class);


    for (Class<?> clz : clzSet) {
      CachedClass cachedClass = ReflectionCache.getCachedClass(clz);
      CachedField[] fields = cachedClass.getFields();
      if(ArrayUtils.isNotEmpty(fields)){
        Map<String,Map<String, Object>> fieldMap = new HashMap<>();
        for (CachedField cachedField : fields) {
          Field annotationField = cachedField.field.getAnnotation(Field.class);
          if(ObjectUtils.isNotEmpty(annotationField)) {
            Map<String, Object> annotationFieldMap = convertAnnotationObjToMap(annotationField);
            fieldMap.put(cachedField.getName(),annotationFieldMap);
          }
        }

        fieldMap.forEach((key,value)-> System.out.println(key+":"+value));
      }
    }

  }


  public static Set<Class<?>> getClasses(String packageName, Class<? extends Annotation> annotationClz){
    Reflections reflections = new Reflections(packageName);
    Set<Class<?>> clzSet =  reflections.getTypesAnnotatedWith(annotationClz);
    return clzSet;
  }


  //AnnotationInvocationHandler,具体查看https://segmentfault.com/a/1190000011213222
  public static Map<String, Object>  convertAnnotationObjToMap(Annotation annotationField) {
    Map<String, Object> annotationFieldMap = new HashMap<>();
    try {
      //获取 annotationField 这个代理实例所持有的 InvocationHandler
      InvocationHandler h = Proxy.getInvocationHandler(annotationField);
      // 获取 AnnotationInvocationHandler 的 memberValues 字段
      java.lang.reflect.Field  hField = h.getClass().getDeclaredField("memberValues");
      // 因为这个字段是 private final 修饰，所以要打开权限
      hField.setAccessible(true);
      // 获取 memberValues
      Map<String, Object> memberValues = (Map<String, Object>) hField.get(h);
      memberValues.forEach((key,value)->{
        if(ObjectUtils.isNotEmpty(value)){
          annotationFieldMap.put(key,value);
        }
      });
      return annotationFieldMap;
    } catch (NoSuchFieldException | IllegalArgumentException |IllegalAccessException e) {
       log.error("convertAnnotationObjToMap error : "+e.getMessage(),e);
    }

    return annotationFieldMap;
  }

  /**
   * 把包含注解值的字段转换为map
   * @param fields
   * @param annotationClz
   * @return
   */
  public static Map<String, Map<String, Object>> covertFieldsIncludeAnnotationValueToMap(CachedField[] fields,Class<? extends Annotation> annotationClz) {
    Map<String, Map<String, Object>> fieldPropertiesMap = new HashMap<>();
    if(ArrayUtils.isNotEmpty(fields)){

      for (CachedField cachedField : fields) {
        Annotation annotationField = cachedField.field.getAnnotation(annotationClz);
        if(ObjectUtils.isNotEmpty(annotationField)) {
          Map<String, Object> annotationFieldPropertiesMap = ReflectionUtil.convertAnnotationObjToMap(annotationField);
          fieldPropertiesMap.put(cachedField.getName(),annotationFieldPropertiesMap);
        }
      }

    }
    return fieldPropertiesMap;
  }

}
