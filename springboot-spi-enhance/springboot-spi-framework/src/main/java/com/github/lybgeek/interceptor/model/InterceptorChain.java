package com.github.lybgeek.interceptor.model;

import cn.hutool.core.collection.CollectionUtil;
import com.github.lybgeek.interceptor.Interceptor;

import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InterceptorChain {

  private final List<Interceptor> interceptors = new ArrayList<>();


  public Object pluginAll(Object target) {
    if(CollectionUtil.isNotEmpty(interceptors)){
      for (Interceptor interceptor : getInterceptors()) {
         target = interceptor.plugin(target);
      }
    }

    return target;
  }

  public InterceptorChain addInterceptor(Interceptor interceptor) {
    interceptors.add(interceptor);
    return this;
  }

  public List<Interceptor> getInterceptors() {
    List<Interceptor> interceptorsByOrder = interceptors.stream().sorted(Comparator.comparing(Interceptor::getOrder).reversed()).collect(Collectors.toList());
    return Collections.unmodifiableList(interceptorsByOrder);
  }

}
