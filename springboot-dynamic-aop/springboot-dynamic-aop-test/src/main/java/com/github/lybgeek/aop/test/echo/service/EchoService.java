package com.github.lybgeek.aop.test.echo.service;

import com.github.lybgeek.aop.test.annotation.TimeCost;
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
public class EchoService implements BeanNameAware, BeanFactoryAware {

    private BeanFactory beanFactory;

    private String beanName;

    @SneakyThrows
    @TimeCost
    public String echo(String message) {
        Object bean = beanFactory.getBean(beanName);
        log.info("============================ {} is Advised : {}",bean, bean instanceof Advised);
        TimeUnit.SECONDS.sleep(new Random().nextInt(3));
        log.info("============================ echo:{}",message);
        return "echo:" + message;

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
