package com.github.lybgeek.interceptor;


import com.github.lybgeek.interceptor.model.Invocation;

public interface Interceptor {

  int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;

  int LOWEST_PRECEDENCE = Integer.MAX_VALUE;

  Object intercept(Invocation invocation) throws Throwable;

  default Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }


  int getOrder();

}
