package com.github.lybgeek.interceptor;


import com.github.lybgeek.groovy.core.event.GroovyBeanRegisterEvent;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.servlet.handler.MappedInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

@Component
public class InterceptorRegisterListener  {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;

    @EventListener
    public void listener(GroovyBeanRegisterEvent event){
        System.out.println(event);
        if(BaseMappedInterceptor.class.isAssignableFrom(event.getBeanClz())){
            BaseMappedInterceptor interceptor = (BaseMappedInterceptor) defaultListableBeanFactory.getBean(event.getBeanName());
            MappedInterceptor mappedInterceptor = build(interceptor);
            registerInterceptor(mappedInterceptor,event.getAliasBeanName() + "_mappedInterceptor");
        }


    }


    public MappedInterceptor build(BaseMappedInterceptor interceptor){
        return new MappedInterceptor(interceptor.getIncludePatterns(),interceptor.getExcludePatterns(),interceptor);
    }

    /**
     * @see org.springframework.web.servlet.handler.AbstractHandlerMapping#initApplicationContext()
     * @See org.springframework.web.servlet.handler.AbstractHandlerMapping#detectMappedInterceptors(java.util.List)
     * @param mappedInterceptor
     * @param beanName
     */
    @SneakyThrows
    public void registerInterceptor(MappedInterceptor mappedInterceptor, String beanName){
        if(defaultListableBeanFactory.containsBean(beanName)){
            unRegisterInterceptor(beanName);
            defaultListableBeanFactory.destroySingleton(beanName);
        }
        //将mappedInterceptor先注册成bean，利用AbstractHandlerMapping#detectMappedInterceptors从spring容器
        //自动检测Interceptor,并加入到当前的拦截器集合中
        defaultListableBeanFactory.registerSingleton(beanName,mappedInterceptor);
        Method method = AbstractHandlerMapping.class.getDeclaredMethod("initApplicationContext");
        method.setAccessible(true);
        method.invoke(requestMappingHandlerMapping);
    }

    @SneakyThrows
    public void unRegisterInterceptor(String beanName){
        MappedInterceptor mappedInterceptor = defaultListableBeanFactory.getBean(beanName,MappedInterceptor.class);
        Field field = AbstractHandlerMapping.class.getDeclaredField("adaptedInterceptors");
        field.setAccessible(true);
        List<HandlerInterceptor> handlerInterceptors = (List<HandlerInterceptor>) field.get(requestMappingHandlerMapping);
        handlerInterceptors.remove(mappedInterceptor);

    }



}
