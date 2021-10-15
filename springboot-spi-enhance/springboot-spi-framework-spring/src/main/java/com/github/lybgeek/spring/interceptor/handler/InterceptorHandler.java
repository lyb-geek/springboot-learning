package com.github.lybgeek.spring.interceptor.handler;


import com.github.lybgeek.interceptor.model.InterceptorChain;

public class InterceptorHandler {

    private InterceptorChain interceptorChain;

    public InterceptorHandler(InterceptorChain interceptorChain) {
        this.interceptorChain = interceptorChain;
    }

    public InterceptorChain getInterceptorChain() {
        return interceptorChain;
    }
}
