package com.github.lybgeek.intercepors;

import com.github.lybgeek.util.HttpConterHelper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class PrometheusInterceptor implements HandlerInterceptor {

  @Autowired
  private HttpConterHelper httpConterHelper;

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {

    httpConterHelper.count();
  }
}
