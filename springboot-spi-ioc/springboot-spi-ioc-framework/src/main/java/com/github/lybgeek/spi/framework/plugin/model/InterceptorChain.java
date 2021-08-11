package com.github.lybgeek.spi.framework.plugin.model;


import com.github.lybgeek.spi.framework.plugin.interceptor.Interceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class InterceptorChain {

  private final List<Interceptor> interceptors = new ArrayList<>();

  private Method method;

  private boolean exposeProxy;

  public void addInterceptor(Interceptor interceptor) {
    interceptors.add(interceptor);
  }

  public List<Interceptor> getInterceptors() {
    List<Interceptor> interceptorsByOrder = interceptors.stream().sorted(Comparator.comparing(Interceptor::getOrder)).collect(Collectors.toList());
    return Collections.unmodifiableList(interceptorsByOrder);
  }

  public Method getMethod() {
    return method;
  }

  public void setMethod(Method method) {
    this.method = method;
  }

  public boolean isExposeProxy() {
    return exposeProxy;
  }

  public void setExposeProxy(boolean exposeProxy) {
    this.exposeProxy = exposeProxy;
  }
}
