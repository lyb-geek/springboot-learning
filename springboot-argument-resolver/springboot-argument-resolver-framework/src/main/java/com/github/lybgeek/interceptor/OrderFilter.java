package com.github.lybgeek.interceptor;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.github.lybgeek.constant.Constant;
import com.github.lybgeek.interceptor.util.RequestBodyUtil;
import com.github.lybgeek.model.Order;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;



public class OrderFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if(servletRequest instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            requestWrapper = new CustomHttpServletRequestWrapper(httpServletRequest);
            //当header的type为filter，由filter负责填充，否则由拦截器负责
            if(Constant.HEADER_VALUE_TYPE_FILTER.equalsIgnoreCase(httpServletRequest.getHeader(Constant.HEADER_KEY_TYPE))){
                System.out.println(">>>>>>>>>>> fillBodyWithId by OrderFilter");
                RequestBodyUtil.fillBodyWithId((CustomHttpServletRequestWrapper) requestWrapper);
            }

        }
        if(requestWrapper == null) {
        	//防止流读取一次就没有了,将流传递下去
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(requestWrapper, servletResponse);
        }
    }
    @Override
    public void destroy() {
    }


}
