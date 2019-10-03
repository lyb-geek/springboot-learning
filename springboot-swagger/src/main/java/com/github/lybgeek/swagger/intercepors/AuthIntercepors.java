package com.github.lybgeek.swagger.intercepors;


import com.github.lybgeek.common.exception.BizException;
import com.github.lybgeek.swagger.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AuthIntercepors implements HandlerInterceptor {



  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {


    String accessToken = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null && cookies.length > 0) {
      for (Cookie cookie : cookies) {
        if (Constant.ACCESS_TOKEN.equals(cookie.getName())) {
          accessToken = cookie.getValue();
          break;
        }
      }
    }

    if (StringUtils.isBlank(accessToken)) {
      accessToken = request.getHeader(Constant.ACCESS_TOKEN);
    }

   if(Constant.ACCESS_TOKEN_PASS_VALUE.equals(accessToken)){
     return true;
   }
    log.error("illegal request uri : {}",request.getRequestURI());
    throw new BizException("非法请求调用",401);

//    response.sendRedirect(request.getContextPath() + "/");
//    return false;
  }
}
