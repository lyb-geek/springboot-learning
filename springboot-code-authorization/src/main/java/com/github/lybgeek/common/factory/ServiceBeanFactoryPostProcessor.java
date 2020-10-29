package com.github.lybgeek.common.factory;

import com.github.lybgeek.common.classloader.CustomClassLoader;
import com.github.lybgeek.common.util.YmlUtils;
import com.github.lybgeek.user.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

@Component
public class ServiceBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Object secretKey = YmlUtils.getValue("lyb-geek.secretKey");
        if(ObjectUtils.isEmpty(secretKey)){
            throw new RuntimeException("secretKey can not be null,you maybe need to config in application.yml with key lyb-geek.secretKey");
        }
        registerBean(beanFactory,secretKey.toString());

//        setClassLoader(beanFactory,secretKey.toString());
    }

    /**
     * 如果项目中引入了>spring-boot-devtools，则默认加载器为org.springframework.boot.devtools.restart.classloader.RestartClassLoader
     * 此时如果使用自定加载器，则需把bean的类加载器变更为AppClassLoader
     * @param beanFactory
     */
    private void setClassLoader(ConfigurableListableBeanFactory beanFactory,String secretKey) {

        CustomClassLoader customClassLoader = new CustomClassLoader(secretKey);
        beanFactory.setBeanClassLoader(customClassLoader.getParent());
    }

    private void registerBean(ConfigurableListableBeanFactory beanFactory,String secretKey){
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(UserService.class);
        GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
        definition.getPropertyValues().add("serviceClz",UserService.class);
        definition.getPropertyValues().add("serviceImplClzName", "com.github.lybgeek.user.service.impl.UserServiceImpl");
        definition.getPropertyValues().add("secretKey", secretKey);
        definition.setBeanClass(ServiceFactoryBean.class);
        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
        String beanId = StringUtils.uncapitalize(UserService.class.getSimpleName());
        defaultListableBeanFactory.registerBeanDefinition(beanId, definition);
    }






}
