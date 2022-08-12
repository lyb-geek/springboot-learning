package com.github.lybgeek.interceptor;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.github.lybgeek.interceptor.util.RequestBodyUtil;
import com.github.lybgeek.model.Order;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class OrderHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            for (MethodParameter methodParameter : handlerMethod.getMethodParameters()) {
                if(methodParameter.getParameterType().isAssignableFrom(Order.class)){
                    if(request instanceof CustomHttpServletRequestWrapper){
                        CustomHttpServletRequestWrapper customHttpServletRequestWrapper = (CustomHttpServletRequestWrapper) request;
                        RequestBodyUtil.fillBodyWithId(customHttpServletRequestWrapper);
                    }
                }
            }
        }

        return true;
    }
}
