package com.github.lybgeek.redis.cacheable.extend.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.MethodInvoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;


/**
 * @description: 标记了缓存注解的方法类信息,用于主动刷新缓存时调用原始方法加载数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class CachedInvocation {

    private CacheMetaData metaData;
    private Object targetBean;
    private Method targetMethod;
    private Object[] arguments;


    public Object invoke()
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final MethodInvoker invoker = new MethodInvoker();
        invoker.setTargetObject(this.getTargetBean());
        invoker.setArguments(this.getArguments());
        invoker.setTargetMethod(this.getTargetMethod().getName());
        invoker.prepare();
        return invoker.invoke();
    }


}

