package com.github.lybgeek.jsonview.factory.init;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.github.lybgeek.jsonview.factory.JsonViewFactory;
import com.github.lybgeek.jsonview.property.JsonViewProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.github.lybgeek.jsonview.factory.JsonViewFactory.JSON_VIEW_FACTORY_SUFFIX;

@RequiredArgsConstructor
@Slf4j
public class JsonViewBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor {

    private ExecutorService executorService = Executors.newSingleThreadExecutor(ThreadUtil.newNamedThreadFactory("json-view-factory-init-thread-", false));
    private final DefaultListableBeanFactory defaultListableBeanFactory;

    private final JsonViewProperty jsonViewProperty;
    private AtomicBoolean init = new AtomicBoolean(false);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            if(jsonViewProperty.isEnabled() && init.compareAndSet(false,true)){
                if(MapUtil.isNotEmpty(jsonViewProperty.getJsonViewFactoryMap())){
                    registerJsonViewFactory(jsonViewProperty.getJsonViewFactoryMap());
                }

            }

        return bean;

    }

    private void registerJsonViewFactory(Map<String,Class<? extends JsonViewFactory>> jsonViewFactoryMap){
        jsonViewFactoryMap.forEach((key,value)->{
            executorService.execute(()->{

                BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(value).getBeanDefinition();
                String alias = key + JSON_VIEW_FACTORY_SUFFIX;
                AnnotationBeanNameGenerator annotationBeanNameGenerator = new AnnotationBeanNameGenerator();
                String beanName = annotationBeanNameGenerator.generateBeanName(beanDefinition, defaultListableBeanFactory);
                defaultListableBeanFactory.registerBeanDefinition(beanName,beanDefinition);
                defaultListableBeanFactory.registerAlias(beanName,alias);
                log.info("register json view factory bean success,beanName:{}",beanName);
            });
        });

    }


}
