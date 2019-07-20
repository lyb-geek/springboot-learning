package com.github.lybgeek.util;

import com.github.lybgeek.model.JdbcConfig;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public enum CacheJdbcConfigUtil {
    INSTANCE;

    private final Map<String, JdbcConfig> cache = new ConcurrentHashMap<>();

    public final String cacheKey = "jdbcKey";

    private AtomicInteger atomicInteger = new AtomicInteger();

    public void put(JdbcConfig jdbcConfig){
        cache.put(cacheKey,jdbcConfig);
    }

    public JdbcConfig getjdbcConfig(){
        return cache.get(cacheKey);
    }


    public JdbcConfig refreshAndGet(JdbcConfig jdbcConfig){
        JdbcConfig cacheJdbcConfig = getjdbcConfig();
        if(StringUtils.isNotBlank(jdbcConfig.getUsername())){
            cacheJdbcConfig.setUsername(jdbcConfig.getUsername());
        }

        if(StringUtils.isNotBlank(jdbcConfig.getUrl())){
            cacheJdbcConfig.setUrl(jdbcConfig.getUrl());
        }

        if(StringUtils.isNotBlank(jdbcConfig.getPassword())){
            cacheJdbcConfig.setPassword(jdbcConfig.getPassword());
        }

        if(StringUtils.isNotBlank(jdbcConfig.getDriverClassName())){
            cacheJdbcConfig.setDriverClassName(jdbcConfig.getDriverClassName());
        }

//        String beanId = JdbcConfig.class.getSimpleName();
//        if(atomicInteger.getAndIncrement() > 0){
//            SpringBeanUtil.unregisterBean(beanId);
//        }
//        SpringBeanUtil.registerBean(beanId,JdbcConfig.class.getName());

        return getjdbcConfig();

    }

}
