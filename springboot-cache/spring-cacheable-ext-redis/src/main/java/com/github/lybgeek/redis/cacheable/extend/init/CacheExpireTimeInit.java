package com.github.lybgeek.redis.cacheable.extend.init;


import cn.hutool.core.map.MapUtil;
import com.github.lybgeek.redis.cacheable.annotation.LybGeekCacheable;
import com.github.lybgeek.redis.cacheable.extend.utils.CacheHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.util.Map;

@Component
@Slf4j
public class CacheExpireTimeInit implements SmartInitializingSingleton, BeanFactoryAware {
    
    private DefaultListableBeanFactory beanFactory;
    
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory)beanFactory;
    }

    @Override
    public void afterSingletonsInstantiated() {
        Map<String, Object> beansWithAnnotation = beanFactory.getBeansWithAnnotation(Component.class);
        if(MapUtil.isNotEmpty(beansWithAnnotation)){
            for (Object cacheValue : beansWithAnnotation.values()) {
                ReflectionUtils.doWithMethods(cacheValue.getClass(), method -> {
                    ReflectionUtils.makeAccessible(method);
                    boolean cacheAnnotationPresent = method.isAnnotationPresent(LybGeekCacheable.class);
                    if(cacheAnnotationPresent){
                         LybGeekCacheable lybGeekCacheable = method.getAnnotation(LybGeekCacheable.class);
                          CacheHelper.initExpireTime(lybGeekCacheable);
                    }

                });
            }
            CacheHelper.initializeCaches();
        }
    }




}
