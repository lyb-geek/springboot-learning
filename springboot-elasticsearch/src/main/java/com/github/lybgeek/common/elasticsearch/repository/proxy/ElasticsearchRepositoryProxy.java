package com.github.lybgeek.common.elasticsearch.repository.proxy;

import com.github.lybgeek.common.elasticsearch.repository.CustomSimpleElasticsearchRepository;
import com.github.lybgeek.common.util.SpringContextUtil;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.codehaus.groovy.reflection.CachedClass;
import org.codehaus.groovy.reflection.CachedMethod;
import org.codehaus.groovy.reflection.ReflectionCache;

@Slf4j
public class ElasticsearchRepositoryProxy implements MethodInterceptor {

    private Class<?> targetClz;

    public  Object getInstance(Class<?> targetClz){
        this.targetClz = targetClz;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClz);
        enhancer.setCallback(this);
        return  enhancer.create();
    }


    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
      CustomSimpleElasticsearchRepository customSimpleElasticsearchRepository = SpringContextUtil.getBean(CustomSimpleElasticsearchRepository.class);
      customSimpleElasticsearchRepository.setEntityClass(this.getEntityClz());
      String mehodName = method.getName();
      log.info("entityClz:{},methodName:{},args:{}",customSimpleElasticsearchRepository.getEntityClass(),mehodName,args);

      return MethodUtils.invokeMethod(customSimpleElasticsearchRepository,true,mehodName,args);

    }

  /**
   * 获取泛型类，比如USER<T>
   * @return 返回T
   */
  private Class<?>  getEntityClz(){

    Type t = targetClz.getGenericInterfaces()[0];
    if(t instanceof ParameterizedType) {
      Type[] p = ((ParameterizedType) t).getActualTypeArguments();
      return (Class<?>) p[0];// 获取第一个类型参数的真实类型
    }

    return null;
  }
}
