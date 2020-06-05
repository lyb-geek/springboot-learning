package com.github.lybgeek.apollo.hystrix.aspect;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.github.lybgeek.apollo.util.AnnotationUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

@Aspect
@Component
@Slf4j
public class HystrixAspect {
    @ApolloConfig
    private Config config;

    /**
     * 缓存方法第一次执行的HystrixProperty的value，key为类名+方法名+HystrixPropertyName
     */
    private Map<String,String> firstHystrixPropertyMap = new ConcurrentHashMap<>();

    @Before(value = "@annotation(hystrixCommand)")
    public void before(JoinPoint jp, HystrixCommand hystrixCommand){
        String keyPrefix = jp.getTarget().getClass().getSimpleName() + "-"+jp.getSignature().getName();
        HystrixProperty[] hystrixProperties = hystrixCommand.commandProperties();
        setHystrixPropertiesByApollo(keyPrefix,hystrixProperties);

        HystrixProperty[] threadPoolProperties = hystrixCommand.threadPoolProperties();
        setHystrixPropertiesByApollo(keyPrefix,threadPoolProperties);

    }

    private void setHystrixPropertiesByApollo(String keyPrefix,HystrixProperty[] hystrixProperties) {
        if(ArrayUtils.isNotEmpty(hystrixProperties)){
            for (HystrixProperty hystrixProperty : hystrixProperties) {
                String key = keyPrefix + "-"+hystrixProperty.name();
                String annotationField;
                if(firstHystrixPropertyMap.containsKey(key)){
                    annotationField = firstHystrixPropertyMap.get(key);
                }else{
                    annotationField = hystrixProperty.value();
                    firstHystrixPropertyMap.put(key,annotationField);
                }
                System.out.println(key+"--->"+annotationField);
                String value = getValueFromApollo(annotationField);
                if(StringUtils.isNotBlank(value)){
                    //清除下hytrix配置属性的缓存，否则就算值已经变更了，仍然不会生效
                    HystrixPropertiesFactory.reset();
                    AnnotationUtils.setAnnotationValue(hystrixProperty,"value",value);

                }

            }
        }
    }

    private String getValueFromApollo(String annotationField){
        String key = StringUtils.deleteWhitespace(annotationField.replace("${", "").replace("}", ""));
        AtomicReference<String> value = new AtomicReference<>(config.getProperty(key, null));
        config.addChangeListener((ConfigChangeEvent changeEvent)->{
            ConfigChange configChange = changeEvent.getChange(key);
            if(ObjectUtils.isNotEmpty(configChange)){
                System.out.println("getValueFromApollo --> change key:"+configChange.getPropertyName()+"---> oldValue:" + configChange.getOldValue()+", newValue:" + configChange.getNewValue());
                value.set(configChange.getNewValue());
            }
        });

        return value.get();
    }


    @After(value = "@annotation(hystrixCommand)")
    public void afterReturing(JoinPoint joinPoint,HystrixCommand hystrixCommand){
        for (HystrixProperty hystrixProperty : hystrixCommand.commandProperties()) {
            System.out.println(hystrixProperty.name() + ":" + hystrixProperty.value());
        }

        for (HystrixProperty hystrixProperty : hystrixCommand.threadPoolProperties()) {
            System.out.println(hystrixProperty.name() + ":" + hystrixProperty.value());
        }
    }
}
