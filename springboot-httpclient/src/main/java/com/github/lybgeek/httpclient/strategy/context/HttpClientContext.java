package com.github.lybgeek.httpclient.strategy.context;

import com.github.lybgeek.common.util.SpringContextHolder;
import com.github.lybgeek.httpclient.annotation.HttpClient;
import com.github.lybgeek.httpclient.enu.HttpclientTypeEnum;
import com.github.lybgeek.httpclient.strategy.HttpClientStrategy;
import org.apache.commons.collections.CollectionUtils;
import org.reflections.Reflections;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public enum HttpClientContext {
   INSTANCE;


   private Map<HttpclientTypeEnum,Class<HttpClientStrategy>> strategyMap = new ConcurrentHashMap<>();


   public void init(String packageName){
     Reflections reflections = new Reflections(packageName);
     Set<Class<?>> clzSet = reflections.getTypesAnnotatedWith(HttpClient.class);
     if(CollectionUtils.isNotEmpty(clzSet)){
       for (Class<?> clz : clzSet) {
           HttpClient httpClient = clz.getAnnotation(HttpClient.class);
           strategyMap.put(httpClient.type(), (Class<HttpClientStrategy>) clz);
       }
     }
   }

   public HttpClientStrategy getInstance(HttpclientTypeEnum type){
     return this.getHttpClientStrategyByType(type);

   }


   private HttpClientStrategy getHttpClientStrategyByType(HttpclientTypeEnum type){
     Class<HttpClientStrategy> clz = strategyMap.get(type);
     Assert.notNull(clz,"type:"+type+"can not found class,please checked");
     return SpringContextHolder.getBean(clz);
   }

}
