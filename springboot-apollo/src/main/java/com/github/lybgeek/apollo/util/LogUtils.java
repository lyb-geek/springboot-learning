package com.github.lybgeek.apollo.util;

import com.github.lybgeek.apollo.annotation.Log;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.*;

public class LogUtils {



    public static List<Log> listLogs(List<String> basePackages,Multimap<String, Class> logBeanMultimap) {
        List<Log> logs = new ArrayList<>();
        for (String basePackage : basePackages) {
            Set<Class> classes = ClassScannerUtils.scan(basePackage, null);
            for (Class clz : classes) {
                Method[] methods = clz.getMethods();
                if (ArrayUtils.isNotEmpty(methods)) {
                    for (Method method : methods) {
                        Log log = method.getAnnotation(Log.class);
                        if (ObjectUtils.isNotEmpty(log)) {
                            logs.add(log);
                            logBeanMultimap.put(Log.class.getSimpleName(),clz);
                        }
                    }
                }
            }
        }

        return logs;
    }

    /**
     * 刷新spring容器需要记录日志的bean
     *
     */
    public static void refreshNeedRecordlogBean(DefaultListableBeanFactory defaultListableBeanFactory,Multimap<String, Class> logBeanMultimap,String annotationAttribute,Object annotationAttributeValue){
        Collection<Class> logBeans = logBeanMultimap.get(Log.class.getSimpleName());
        if(!CollectionUtils.isEmpty(logBeans)){
            for (Class logBean : logBeans) {
                ScannedGenericBeanDefinition beanDefinition = (ScannedGenericBeanDefinition)defaultListableBeanFactory.getBeanDefinition(StringUtils.uncapitalize(logBean.getSimpleName()));
                AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
                //返回所有的标注有指定注解的方法元信息
                Set<MethodMetadata> methodMetadataSet = annotationMetadata.getAnnotatedMethods(Log.class.getName());
                if(!CollectionUtils.isEmpty(methodMetadataSet)){
                    for (MethodMetadata methodMetadata : methodMetadataSet) {
                        Map<String, Object> annotationAttributeMap = methodMetadata.getAnnotationAttributes(Log.class.getName());
                        if(ObjectUtils.isNotEmpty(annotationAttributeMap) && annotationAttributeMap.containsKey(annotationAttribute)){
                            annotationAttributeMap.put(annotationAttribute,annotationAttributeValue);
                        }
                    }
                }
                beanDefinition.setBeanClassName(logBean.getName());
                defaultListableBeanFactory.registerBeanDefinition( StringUtils.uncapitalize(logBean.getSimpleName()), beanDefinition);

            }
        }
    }
}
