package com.github.lybgeek.feign.process;

import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;


public class FeignClientsServiceNameAppendBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware , EnvironmentAware {

    private ApplicationContext applicationContext;

    private Environment environment;

    private AtomicInteger atomicInteger = new AtomicInteger();

    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if(atomicInteger.getAndIncrement() == 0){
            String beanNameOfFeignClientFactoryBean = "org.springframework.cloud.openfeign.FeignClientFactoryBean";
            Class beanNameClz = Class.forName(beanNameOfFeignClientFactoryBean);

            applicationContext.getBeansOfType(beanNameClz).forEach((feignBeanName,beanOfFeignClientFactoryBean)->{
                try {
                    setField(beanNameClz,"name",beanOfFeignClientFactoryBean);
                    setField(beanNameClz,"url",beanOfFeignClientFactoryBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println(feignBeanName + "-->" + beanOfFeignClientFactoryBean);
            });
        }


        return null;
    }

    private  void setField(Class clazz, String fieldName, Object obj) throws Exception{

        Field field = ReflectionUtils.findField(clazz, fieldName);
        if(Objects.nonNull(field)){
            ReflectionUtils.makeAccessible(field);
            Object value = field.get(obj);
            if(Objects.nonNull(value)){
                value = value.toString().replace("env",environment.getProperty("feign.env"));
                ReflectionUtils.setField(field, obj, value);
            }


        }



    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
