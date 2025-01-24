package com.github.lybgeek.chain.test.interceptor;


import com.github.lybgeek.chain.delegete.CommandDelegete;
import lombok.RequiredArgsConstructor;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.web.servlet.ServletWebContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class UserHandlerInterceptor implements HandlerInterceptor {


    private final CommandDelegete commandDelegete;

    @Autowired
    private ServletContext servletContext;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Context context = new ServletWebContext(servletContext, request, response);
        commandDelegete.execute(context);

        return true;
    }





}
