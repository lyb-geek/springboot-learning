package com.gitee.lybgeek.scan.factory;

import com.gitee.lybgeek.service.HelloService;
import com.gitee.lybgeek.service.impl.HelloServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.StringUtils;

@Slf4j
public class HelloSvcBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String beanName = StringUtils.uncapitalize(HelloService.class.getSimpleName());
        log.info("register bean : beanName:{}",beanName);

        beanFactory.registerSingleton(beanName,new HelloServiceImpl());
    }
}
