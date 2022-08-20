package com.github.lybgeek.event.listener;


import com.github.lybgeek.event.LybGeekApplicationEventMulticaster;
import com.github.lybgeek.event.LybGeekApplicationListenerMethodAdapter;
import com.github.lybgeek.event.listener.annotation.LybGeekEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Map;

@Slf4j
public class LybGeekEventListenerMethodProcessor implements SmartInitializingSingleton, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    @Override
    public void afterSingletonsInstantiated() {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = beanFactory.getBean(beanDefinitionName);
            Map<Method, LybGeekEventListener> annotatedMethods = null;
            try {
                annotatedMethods = MethodIntrospector.selectMethods(bean.getClass(),
                        (MethodIntrospector.MetadataLookup<LybGeekEventListener>) method ->
                                AnnotatedElementUtils.findMergedAnnotation(method, LybGeekEventListener.class));
            }
            catch (Throwable ex) {
                // An unresolvable type in a method signature, probably from a lazy bean - let's ignore it.
                if (log.isDebugEnabled()) {
                    log.debug("Could not resolve methods for bean with name '" + beanDefinitionName + "'", ex);
                }
            }

            if(!CollectionUtils.isEmpty(annotatedMethods)){
                for (Method method : annotatedMethods.keySet()) {
                        Method methodToUse = AopUtils.selectInvocableMethod(method, beanFactory.getType(beanDefinitionName));
                        ApplicationListener<?> applicationListener = new LybGeekApplicationListenerMethodAdapter(beanDefinitionName,bean,methodToUse);
                        beanFactory.getBean(LybGeekApplicationEventMulticaster.class).addApplicationListener(applicationListener);
                    }
                }
            }


        }
        


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory)beanFactory;
    }
}
