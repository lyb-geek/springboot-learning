package com.github.lybgeek.swagger.test.user.interceptor;


import com.github.lybgeek.swagger.test.user.exception.UserException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if("123456".equals(token)){
            return true;
        }

        throw new UserException("用户鉴权没通过", 401);

    }
}
