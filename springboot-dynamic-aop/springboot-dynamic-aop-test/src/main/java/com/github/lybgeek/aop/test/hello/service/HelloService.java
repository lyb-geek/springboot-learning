package com.github.lybgeek.aop.test.hello.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class HelloService implements BeanNameAware, BeanFactoryAware {
    private BeanFactory beanFactory;

    private String beanName;

    @SneakyThrows
    public String sayHello(String message) {
        Object bean = beanFactory.getBean(beanName);
        log.info("============================ {} is Advised : {}",bean, bean instanceof Advised);
        TimeUnit.SECONDS.sleep(new Random().nextInt(3));
        log.info("============================ hello:{}",message);

        return "hello:" + message;

    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }
}
