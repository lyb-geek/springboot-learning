package com.github.lybgeek.apollo.factory;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.github.lybgeek.apollo.util.ClassScannerUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class RefreshBeanFactory implements BeanFactoryPostProcessor {



    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        Config config = ConfigService.getConfig("order.properties");
        List<String> basePackages = AutoConfigurationPackages.get(configurableListableBeanFactory);
        for (String basePackage : basePackages) {
            Set<Class> conditionalClasses = ClassScannerUtils.scan(basePackage, ConditionalOnProperty.class);
            if(!CollectionUtils.isEmpty(conditionalClasses)){
                for (Class conditionalClass : conditionalClasses) {
                    ConditionalOnProperty conditionalOnProperty = (ConditionalOnProperty) conditionalClass.getAnnotation(ConditionalOnProperty.class);
                    String[] conditionalOnPropertyKeys = conditionalOnProperty.name();
                    String beanConditionKey = this.getConditionalOnPropertyKey(config,conditionalOnPropertyKeys);
                    String conditionalOnPropertyValue = conditionalOnProperty.havingValue();
                    this.registerBeanIfMatchCondition((DefaultListableBeanFactory)configurableListableBeanFactory,config,conditionalClass,beanConditionKey,conditionalOnPropertyValue);
                }
            }
        }


    }

    private void registerBeanIfMatchCondition(DefaultListableBeanFactory beanFactory,Config config,Class conditionalClass, String beanConditionKey, String conditionalOnPropertyValue) {
        boolean isNeedRegisterBean = this.isNeedRegisterBean(config,beanConditionKey,conditionalOnPropertyValue);
        String beanName = StringUtils.uncapitalize(conditionalClass.getSimpleName());
        if(isNeedRegisterBean){
                this.registerBean(config,beanFactory,beanName,conditionalClass);

        }

    }

    public void registerBean(Config config,DefaultListableBeanFactory beanFactory, String beanName, Class beanClass) {
        log.info("registerBean->beanName:{},beanClass:{}",beanName,beanClass);
        BeanDefinitionBuilder beanDefinitionBurinilder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
        BeanDefinition beanDefinition = beanDefinitionBurinilder.getBeanDefinition();
        setBeanField(config,beanClass, beanDefinition);
        beanFactory.registerBeanDefinition(beanName,beanDefinition);


    }

    private void setBeanField(Config config,Class beanClass, BeanDefinition beanDefinition) {
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

    public boolean isNeedRegisterBean(Config config,String beanConditionKey,String conditionalOnPropertyValue){
        if(StringUtils.isEmpty(beanConditionKey)){
            return false;
        }
        String apolloConfigValue = config.getProperty(beanConditionKey,null);
        return conditionalOnPropertyValue.equals(apolloConfigValue);
    }


    private String getConditionalOnPropertyKey(Config config, String[] conditionalOnPropertyKeys){
        if(ArrayUtils.isEmpty(conditionalOnPropertyKeys)){
            return null;
        }
        String changeKey = null;
        for (String conditionalOnPropertyKey : conditionalOnPropertyKeys) {
            if(isConditionalOnPropertyKey(config,conditionalOnPropertyKey)){
                changeKey = conditionalOnPropertyKey;
                break;
            }
        }

        return changeKey;
    }

    private boolean isConditionalOnPropertyKey(Config config,String conditionalOnPropertyKey){
        Set<String> propertyNames = config.getPropertyNames();
        if(!CollectionUtils.isEmpty(propertyNames) && propertyNames.contains(conditionalOnPropertyKey)){
            return true;
        }
        return false;
    }



}
