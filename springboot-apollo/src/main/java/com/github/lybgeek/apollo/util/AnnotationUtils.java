package com.github.lybgeek.apollo.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

@Slf4j
public class AnnotationUtils {

    /**
     * 设置注解属性值
     * @param annotation
     * @param annotationKey
     * @param annotationValue
     */
    public static void setAnnotationValue(Object annotation,String annotationKey,Object annotationValue){
        try {
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
            Field memberValuesField = invocationHandler.getClass().getDeclaredField("memberValues");
            memberValuesField.setAccessible(true);
            Map memberValues = (Map)memberValuesField.get(invocationHandler);
            memberValues.put(annotationKey, annotationValue);
        } catch (Exception e) {
           log.error("setAnnotationValue fail:"+e.getMessage(),e);
        }

    }
}
