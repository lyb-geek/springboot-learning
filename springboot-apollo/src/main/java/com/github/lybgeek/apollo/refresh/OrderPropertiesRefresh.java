package com.github.lybgeek.apollo.refresh;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.github.lybgeek.apollo.annotation.RefreshBean;
import com.github.lybgeek.apollo.cache.BeanCache;
import com.github.lybgeek.apollo.util.PrintChangeKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

@Component
@Slf4j
public class OrderPropertiesRefresh implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @ApolloConfig(value = "order.properties")
    private Config config;

    @Autowired
    private BeanCache beanCache;


    @ApolloConfigChangeListener(value="order.properties",interestedKeyPrefixes = {"order."},interestedKeys = {"model.isShowOrder"})
    private void refresh(ConfigChangeEvent changeEvent){
            Collection<Class> conditionalClasses = beanCache.getConditionalClassesMap().values();
            if(!CollectionUtils.isEmpty(conditionalClasses)){
                for (Class conditionalClass : conditionalClasses) {
                    ConditionalOnProperty conditionalOnProperty = (ConditionalOnProperty) conditionalClass.getAnnotation(ConditionalOnProperty.class);
                    String[] conditionalOnPropertyKeys = conditionalOnProperty.name();
                    String beanChangeCondition = this.getChangeKey(changeEvent,conditionalOnPropertyKeys);
                    String conditionalOnPropertyValue = conditionalOnProperty.havingValue();
                    boolean isChangeBean = this.changeBean(conditionalClass, beanChangeCondition, conditionalOnPropertyValue);
                    if(!isChangeBean){
                        // 更新相应的bean的属性值，主要是存在@ConfigurationProperties注解的bean
                        applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));
                    }
                }
            }



        PrintChangeKeyUtils.printChange(changeEvent);
        printAllBeans();
    }


    /**
     * 根据条件对bean进行注册或者移除
     * @param conditionalClass
     * @param beanChangeCondition bean发生改变的条件
     * @param conditionalOnPropertyValue
     */
    private boolean changeBean(Class conditionalClass, String beanChangeCondition, String conditionalOnPropertyValue) {
        boolean isNeedRegisterBeanIfKeyChange = this.isNeedRegisterBeanIfKeyChange(beanChangeCondition,conditionalOnPropertyValue);
        boolean isNeedRemoveBeanIfKeyChange = this.isNeedRemoveBeanIfKeyChange(beanChangeCondition,conditionalOnPropertyValue);
        String beanName = StringUtils.uncapitalize(conditionalClass.getSimpleName());
        if(isNeedRegisterBeanIfKeyChange){
            boolean isAlreadyRegisterBean = this.isExistBean(beanName);
            if(!isAlreadyRegisterBean){
                this.registerBean(beanName,conditionalClass);
                return true;
            }
        }else if(isNeedRemoveBeanIfKeyChange){
            this.unregisterBean(beanName);
            this.refreshBeanDependChangeBean(conditionalClass);
            return true;
        }
        return false;
    }

    /**
     * bean注册
     * @param beanName
     * @param beanClass
     */
    public void registerBean(String beanName,Class beanClass) {
        log.info("registerBean->beanName:{},beanClass:{}",beanName,beanClass);
        BeanDefinitionBuilder beanDefinitionBurinilder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
        BeanDefinition beanDefinition = beanDefinitionBurinilder.getBeanDefinition();
        setBeanField(beanClass, beanDefinition);
        getBeanDefinitionRegistry().registerBeanDefinition(beanName,beanDefinition);
        refreshBeanDependChangeBean(beanClass);

    }

    /**
     * 设置bean字段值
     * @param beanClass
     * @param beanDefinition
     */
    private void setBeanField(Class beanClass, BeanDefinition beanDefinition) {
        ConfigurationProperties configurationProperties = (ConfigurationProperties) beanClass.getAnnotation(ConfigurationProperties.class);
        if(ObjectUtils.isNotEmpty(configurationProperties)){
            String prefix = configurationProperties.prefix();
            for (String propertyName : config.getPropertyNames()) {
                String fieldPrefix = prefix + ".";
                if(propertyName.startsWith(fieldPrefix)){
                    String fieldName = propertyName.substring(fieldPrefix.length());
                    String fieldVal = config.getProperty(propertyName,null);
                    log.info("setBeanField-->fieldName:{},fieldVal:{}",fieldName,fieldVal);
                    beanDefinition.getPropertyValues().add(fieldName,fieldVal);
                }
            }
        }
    }



    /**
     * bean移除
     * @param beanName
     */
    public void unregisterBean(String beanName){
        log.info("unregisterBean->beanName:{}",beanName);
        getBeanDefinitionRegistry().removeBeanDefinition(beanName);
    }




    public  <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    public  <T> T getBean(Class<T> clz) {
        return (T) applicationContext.getBean(clz);
    }

    public boolean isExistBean(String beanName){
        return applicationContext.containsBean(beanName);
    }

    public boolean isExistBean(Class clz){
        try {
            Object bean = applicationContext.getBean(clz);
            return true;
        } catch (BeansException e) {
            // log.error(e.getMessage(),e);
        }
        return false;
    }

    public boolean isNeedRegisterBeanIfKeyChange(String changeKey,String conditionalOnPropertyValue){
        if(StringUtils.isEmpty(changeKey)){
            return false;
        }
        String apolloConfigValue = config.getProperty(changeKey,null);
        return conditionalOnPropertyValue.equals(apolloConfigValue);
    }

    private boolean isNeedRemoveBeanIfKeyChange(String changeKey,String conditionalOnPropertyValue){
        if(!StringUtils.isEmpty(changeKey)){
            String apolloConfigValue = config.getProperty(changeKey,null);
            return !conditionalOnPropertyValue.equals(apolloConfigValue);
        }

        return false;

    }

    private boolean isChangeKey(ConfigChangeEvent changeEvent,String conditionalOnPropertyKey){
        Set<String> changeKeys = changeEvent.changedKeys();
        if(!CollectionUtils.isEmpty(changeKeys) && changeKeys.contains(conditionalOnPropertyKey)){
            return true;
        }
        return false;
    }

    private String getChangeKey(ConfigChangeEvent changeEvent, String[] conditionalOnPropertyKeys){
        if(ArrayUtils.isEmpty(conditionalOnPropertyKeys)){
            return null;
        }
        String changeKey = null;
        for (String conditionalOnPropertyKey : conditionalOnPropertyKeys) {
            if(isChangeKey(changeEvent,conditionalOnPropertyKey)){
                changeKey = conditionalOnPropertyKey;
                break;
            }
        }

        return changeKey;
    }


    /**
     * 刷新依赖order的bean
     * @param conditionalClass
     */
    private void refreshBeanDependChangeBean(final Class conditionalClass){
        beanCache.getRefreshBeanClassesMap().forEach((refreshBeanName,refreshBeanClass)->{
            RefreshBean refreshBean = (RefreshBean) refreshBeanClass.getAnnotation(RefreshBean.class);
            for (Class fieldBeanClass : refreshBean.refreshFieldBeans()) {
                if(conditionalClass.getName().equals(fieldBeanClass.getName())){
                    log.info("refreshBeanDependChangeBean-->refreshBeanName:{},refreshBeanClass:{}",refreshBeanName,refreshBeanClass);
                    BeanDefinition beanDefinition = getBeanDefinitionRegistry().getBeanDefinition(refreshBeanName);
                    beanDefinition.setBeanClassName(refreshBeanClass.getName());
                    getBeanDefinitionRegistry().registerBeanDefinition(refreshBeanName, beanDefinition);
                }
            }

        });
        
    }

    private BeanDefinitionRegistry getBeanDefinitionRegistry(){
        ConfigurableApplicationContext configurableContext = (ConfigurableApplicationContext) applicationContext;
        BeanDefinitionRegistry beanDefinitionRegistry = (DefaultListableBeanFactory) configurableContext.getBeanFactory();
        return beanDefinitionRegistry;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public  void printAllBeans() {
        String[] beans = applicationContext.getBeanDefinitionNames();
        Arrays.sort(beans);
        for (String beanName : beans) {
            Class<?> beanType = applicationContext.getType(beanName);
            System.out.println(beanType);
        }
    }

}
