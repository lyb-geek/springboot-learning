package com.github.lybgeek.aop.util;


import lombok.SneakyThrows;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.github.lybgeek.aop.core.constant.Constant.SPIILT;

public final class UrlClassUtil {


    private UrlClassUtil(){}

    private static Map<String,Class> targetClzMap = new ConcurrentHashMap<>();

    private static Map<String,Object> targetObjMap = new ConcurrentHashMap<>();

    @SneakyThrows
    public static Class getClz(String targetClassUrl, String className){
        if(targetClzMap.containsKey(targetClassUrl + SPIILT + className)){
            return targetClzMap.get(targetClassUrl + SPIILT + className);
        }
        URL url = new URL(targetClassUrl);
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});
        Class clz = urlClassLoader.loadClass(className);
        targetClzMap.put(targetClassUrl + SPIILT + className,clz);
        return clz;
    }

    @SneakyThrows
    public static Object getObj(String targetClassUrl, String className){
        if(targetObjMap.containsKey(targetClassUrl + SPIILT + className)){
            return targetObjMap.get(targetClassUrl + SPIILT + className);
        }
        Class targetClz = getClz(targetClassUrl,className);
        Object obj = targetClz.getConstructor().newInstance();
        targetObjMap.put(targetClassUrl + SPIILT + className,obj);
        return obj;
    }
}

