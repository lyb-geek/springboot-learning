package com.github.lybgeek.spi.framework.factory;


import cn.hutool.core.util.ObjectUtil;
import com.github.lybgeek.spi.framework.plugin.anotation.InterceptorMethod;
import com.github.lybgeek.spi.framework.plugin.interceptor.Interceptor;
import com.github.lybgeek.spi.framework.plugin.model.InterceptorChain;
import com.github.lybgeek.spi.framework.plugin.proxy.SpiProxy;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class SpiFactory<T> {

    private final Map<String,T> spiMap = new ConcurrentHashMap<>(1024);

    /**
     * key: interceptor className
     * value : interceptor
     */
    private final Map<String,Object> interceptorMap = new LinkedHashMap<>();

    public List<T> listServices(Class<T> clz){
        return listServicesAndPutMapIfNecessary(clz,false);
    }

    public Map<String,T> getSpiMap(Class<T> clz){
        listServicesAndPutMapIfNecessary(clz,true);
        return spiMap;
    }


    private List<T> listServicesAndPutMapIfNecessary(Class<T> clz,boolean isNeedPutMap){
        List<T> serviceList = new ArrayList();
        ServiceLoader<T> services = ServiceLoader.load(clz);
        Iterator<T> iterator = services.iterator();
        while(iterator.hasNext()){
            T service = iterator.next();
            serviceList.add(service);
            setSevices2Map(isNeedPutMap, service);
        }
        return serviceList;
    }

    @SneakyThrows
    private void setSevices2Map(boolean isNeedPutMap, T service) {
        if(isNeedPutMap){
            String serviceName = StringUtils.uncapitalize(service.getClass().getSimpleName());
            service = getProxyIfIfNecessary(service);

            spiMap.put(serviceName,service);
        }
    }

    private T getProxyIfIfNecessary(T service) throws InstantiationException, IllegalAccessException {
        InterceptorChain interceptorChain = getInterceptorChain(service);
        if(interceptorChain.isExposeProxy()){
            Map<Class<?>, InterceptorChain> signatureMap = new HashMap<>();
            signatureMap.put(service.getClass(),interceptorChain);
            SpiProxy spiProxy = new SpiProxy(service,signatureMap);
            service = (T) Proxy.newProxyInstance(service.getClass().getClassLoader(),service.getClass().getInterfaces(),spiProxy);
        }
        return service;
    }

    private InterceptorChain getInterceptorChain(T service) throws InstantiationException, IllegalAccessException {
        InterceptorChain interceptorChain = new InterceptorChain();

        synchronized (interceptorMap){
            for (Method declaredMethod : service.getClass().getDeclaredMethods()) {
                InterceptorMethod interceptorMethod = declaredMethod.getDeclaredAnnotation(InterceptorMethod.class);
                if(ObjectUtil.isNotNull(interceptorMethod)){
                    interceptorChain.setMethod(declaredMethod);
                    interceptorChain.setExposeProxy(true);
                    Class<? extends Interceptor>[] interceptorClasses = interceptorMethod.interceptorClasses();
                    for (Class<? extends Interceptor> interceptorClass : interceptorClasses) {
                        String interceptorClassName = interceptorClass.getName();
                        Interceptor interceptor = (Interceptor) interceptorMap.get(interceptorClassName);
                        if(ObjectUtil.isNull(interceptor)){
                            interceptor = interceptorClass.newInstance();
                            interceptorMap.put(interceptorClassName,interceptor);
                        }
                        interceptorChain.addInterceptor(interceptor);
                    }
                }
            }
        }
        return interceptorChain;
    }
}
