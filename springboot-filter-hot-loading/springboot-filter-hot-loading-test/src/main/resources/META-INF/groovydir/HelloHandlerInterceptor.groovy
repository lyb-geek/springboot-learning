package com.github.lybgeek.interceptor;


import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Component
public class HelloHandlerInterceptor extends BaseMappedInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("uri:" + request.getRequestURI());
       return true;

    }

    @Override
    public String[] getIncludePatterns() {
        return ["/**"];
    }

    @Override
    public String[] getExcludePatterns() {
        return new String[0];
    }
}
