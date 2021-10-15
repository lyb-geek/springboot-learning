package com.github.lybgeek.spring.spi.factory;


import com.github.lybgeek.spi.anotatation.Activate;
import com.github.lybgeek.spi.anotatation.SPI;
import com.github.lybgeek.spi.extension.ExtensionLoader;
import com.github.lybgeek.spring.interceptor.handler.InterceptorHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * BeanPostProcessor本身也是一个Bean，一般而言其实例化时机要早过普通的Bean，
 * 但是BeanPostProcessor有时也会依赖一些Bean，
 * 这就导致了一些普通Bean的实例化早于BeanPostProcessor的可能情况,而引发一些情况，
 * 比如这些提前初始化的bean无法享有一些后置处理器扩展的功能
 *
 * 比如本例中SpiInstancePostProcessor因为调用interceptorHandler.getInterceptorChain().pluginAll(bean)，会导致
 * InterceptorHandler，以及相应的拦截器bean先于BeanPostProcessor初始化，导致项目启动会报
 * is not eligible for getting processed by all BeanPostProcessors.
 */
@Slf4j
@Deprecated
public class SpiInstancePostProcessor implements BeanPostProcessor {

    private DefaultListableBeanFactory beanFactory;

    private InterceptorHandler interceptorHandler;

    public SpiInstancePostProcessor(InterceptorHandler interceptorHandler,DefaultListableBeanFactory beanFactory) {
        this.interceptorHandler = interceptorHandler;
        this.beanFactory = beanFactory;
    }


    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean.getClass().isAnnotationPresent(Activate.class)){
            return interceptorHandler.getInterceptorChain().pluginAll(bean);
        }
        return bean;
    }






}
