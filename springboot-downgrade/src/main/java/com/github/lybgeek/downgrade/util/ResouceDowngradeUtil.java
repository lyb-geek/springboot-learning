package com.github.lybgeek.downgrade.util;

import com.github.lybgeek.downgrade.annotation.ResouceDowngrade;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Slf4j
public enum ResouceDowngradeUtil {

  INSTANCE;

  public static final String packageName = "com.github.lybgeek.downgrade.service";

  public static final String MAX_THRESHOLD_KEY = "maxThreshold";

  private Map<String, ResouceDowngrade> resouceId2AnnotaionMap = new ConcurrentHashMap<>();

  public void init(){
    Reflections reflections = new Reflections(packageName,new MethodAnnotationsScanner());
    Set<Method> methodSet = reflections.getMethodsAnnotatedWith(ResouceDowngrade.class);
    if(!CollectionUtils.isEmpty(methodSet)){
      for (Method method : methodSet) {
        ResouceDowngrade resouceDowngrade = method.getAnnotation(ResouceDowngrade.class);
        resouceId2AnnotaionMap.put(resouceDowngrade.resouceId(),resouceDowngrade);
      }
    }

  }

  public ResouceDowngrade getResouceDowngradeByResouceId(String resouceId){
    ResouceDowngrade resouceDowngrade = resouceId2AnnotaionMap.get(resouceId);
    Assert.notNull(resouceDowngrade," resouceId : "+ resouceId + " can not found resouceDowngrade");
    return resouceDowngrade;
  }


  public void changeThreshold(String resouceId,Integer threshold){
    ResouceDowngrade resouceDowngrade = this.getResouceDowngradeByResouceId(resouceId);
    InvocationHandler invocationHandler = Proxy.getInvocationHandler(resouceDowngrade);
    try {
      Field field = invocationHandler.getClass().getDeclaredField("memberValues");
      field.setAccessible(true);
      Map<String, Object> memberValues = (Map<String, Object>) field.get(invocationHandler);
      if(memberValues.containsKey(MAX_THRESHOLD_KEY)){
         memberValues.put(MAX_THRESHOLD_KEY,threshold);
         log.info("resouceId: {} change maxThreshold to {} ok !",resouceId,threshold);
      }
    } catch (NoSuchFieldException | IllegalAccessException e) {
       log.error("changeThreshold error:"+e.getMessage(),e);
    }

  }




}
