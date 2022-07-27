package com.github.lybgeek.redis.cache;

import java.lang.reflect.Method;
import java.util.Set;


/**
 * @description: 缓存方法注册接口
 **/
public interface InvocationRegistry {

	void registerInvocation(Object invokedBean, Method invokedMethod, Object[] invocationArguments, Set<String> cacheNames);

}