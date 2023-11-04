package com.github.lybgeek.interceptor;


import org.springframework.web.servlet.HandlerInterceptor;

public abstract class BaseMappedInterceptor implements HandlerInterceptor {

    public abstract String[] getIncludePatterns();

    public abstract String[] getExcludePatterns();

}
