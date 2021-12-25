package com.github.lybgeek.circuitbreaker.framework.ciruitbreaker.origin;


import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;

import javax.servlet.http.HttpServletRequest;

public class QueryStringRequestOriginParser implements RequestOriginParser {

    public static final String ORIGIN_KEY = "origin";

    @Override
    public String parseOrigin(HttpServletRequest request) {
        String origin = request.getParameter(ORIGIN_KEY);
        return origin;
    }
}
