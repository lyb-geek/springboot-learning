package com.github.lybgeek.circuitbreaker.spi.proxy.util;


import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

public class ClassUtils {

    public static String getClassName(Object target){
        String className = StringUtils.split(target.toString(),"@")[0];
        return className;
    }

    @SneakyThrows
    public static Class getClassType(Object target){
        String className = getClassName(target);
        Class classType = Class.forName(className);
        return classType;
    }
}
