package com.github.lybgeek.chaincontext.hystrix;

import com.github.lybgeek.chaincontext.ChainContextHolder;

import java.util.Map;
import java.util.concurrent.Callable;


public final class DelegatingChainContextCallable<V> implements Callable<V>{

    private final Callable<V> delegate;
    
    private Map<String, Object> chaincontextAttributes;
    
    public DelegatingChainContextCallable(Callable<V> delegate, Map<String, Object> chaincontextAttributes) {
        this.delegate = delegate;
        this.chaincontextAttributes = chaincontextAttributes;
    }

    @Override
    public V call() throws Exception {
        try {
            ChainContextHolder.getCurrentContext().putAll(chaincontextAttributes);
            return delegate.call();
        } finally {
            ChainContextHolder.getCurrentContext().unset();
        }
        
    }
    
}
