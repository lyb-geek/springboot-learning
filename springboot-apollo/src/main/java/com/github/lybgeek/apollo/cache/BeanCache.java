package com.github.lybgeek.apollo.cache;

import com.github.lybgeek.apollo.annotation.RefreshBean;
import com.github.lybgeek.apollo.util.ClassScannerUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@Getter
public class BeanCache implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private Map<String,Class> conditionalClassesMap = new ConcurrentHashMap<>();

    private Map<String,Class> refreshBeanClassesMap = new ConcurrentHashMap<>();


    @PostConstruct
    public void init(){
        for (String basePackage : listBasePackages()){
            Set<Class> conditionalClasses = ClassScannerUtils.scan(basePackage, ConditionalOnProperty.class);
            if(!CollectionUtils.isEmpty(conditionalClasses)){
                for (Class conditionalClass : conditionalClasses) {
                    String beanName = StringUtils.uncapitalize(conditionalClass.getSimpleName());
                    conditionalClassesMap.put(beanName,conditionalClass);
                }
            }


            Set<Class> refreshBeanClasses = ClassScannerUtils.scan(basePackage, RefreshBean.class);
            if(!CollectionUtils.isEmpty(refreshBeanClasses)){
                for (Class refreshBeanClass : refreshBeanClasses) {
                    String beanName = StringUtils.uncapitalize(refreshBeanClass.getSimpleName());
                    refreshBeanClassesMap.put(beanName,refreshBeanClass);
                }
            }
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private List<String> listBasePackages(){
        ConfigurableApplicationContext configurableContext = (ConfigurableApplicationContext) applicationContext;
        return AutoConfigurationPackages.get(configurableContext.getBeanFactory());
    }
}
