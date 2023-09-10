package com.github.lybgeek.aop.core.util;


import com.github.lybgeek.aop.core.locator.enums.OperateEventEnum;
import com.github.lybgeek.aop.core.model.ProxyMetaInfo;
import com.github.lybgeek.aop.util.UrlClassUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Supplier;

import static com.github.lybgeek.aop.core.constant.Constant.SPIILT;

@Slf4j
public final class AopUtil {

    public static final String PROXY_PLUGIN_PREFIX = "lybgeekAdvisor" + SPIILT;

    private AopUtil(){}

    @SneakyThrows
    public static MethodInterceptor getMethodInterceptor(String targetClassUrl, String className){
        Object obj = UrlClassUtil.getObj(targetClassUrl,className);
        if(obj instanceof MethodInterceptor){
            return (MethodInterceptor) obj;
        }
        return null;
    }

    public static <T> T getProxy(ProxyMetaInfo proxyMetaInfo){
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory();
        proxyFactory.setTarget(proxyMetaInfo.getTarget());
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(proxyMetaInfo.getPointcut());
        MethodInterceptor methodInterceptor = AopUtil.getMethodInterceptor(proxyMetaInfo.getProxyUrl(),proxyMetaInfo.getProxyClassName());
        proxyFactory.addAdvisor(new DefaultPointcutAdvisor(pointcut,methodInterceptor));
        return proxyFactory.getProxy();
    }

    public static void registerProxy(DefaultListableBeanFactory beanFactory,ProxyMetaInfo proxyMetaInfo){
        AspectJExpressionPointcutAdvisor advisor = getAspectJExpressionPointcutAdvisor(beanFactory, proxyMetaInfo);
        addOrDelAdvice(beanFactory,OperateEventEnum.ADD,advisor);

    }

    private static AspectJExpressionPointcutAdvisor getAspectJExpressionPointcutAdvisor(DefaultListableBeanFactory beanFactory, ProxyMetaInfo proxyMetaInfo) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();
        GenericBeanDefinition beanDefinition = (GenericBeanDefinition) builder.getBeanDefinition();
        beanDefinition.setBeanClass(AspectJExpressionPointcutAdvisor.class);
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(proxyMetaInfo.getPointcut());
        advisor.setAdvice(Objects.requireNonNull(getMethodInterceptor(proxyMetaInfo.getProxyUrl(), proxyMetaInfo.getProxyClassName())));
        beanDefinition.setInstanceSupplier((Supplier<AspectJExpressionPointcutAdvisor>) () -> advisor);
        beanFactory.registerBeanDefinition(PROXY_PLUGIN_PREFIX + proxyMetaInfo.getId(),beanDefinition);

        return advisor;
    }



    public static void destoryProxy(DefaultListableBeanFactory beanFactory,String id){
        String beanName = PROXY_PLUGIN_PREFIX + id;
        if(beanFactory.containsBean(beanName)){
            AspectJExpressionPointcutAdvisor advisor = beanFactory.getBean(beanName,AspectJExpressionPointcutAdvisor.class);
            addOrDelAdvice(beanFactory,OperateEventEnum.DEL,advisor);
            beanFactory.destroyBean(beanFactory.getBean(beanName));
        }
    }

    public static void addOrDelAdvice(DefaultListableBeanFactory beanFactory, OperateEventEnum operateEventEnum,AspectJExpressionPointcutAdvisor advisor){
        AspectJExpressionPointcut pointcut = (AspectJExpressionPointcut) advisor.getPointcut();
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            Object bean = beanFactory.getBean(beanDefinitionName);
            if(!(bean instanceof Advised)){
                if(operateEventEnum == OperateEventEnum.ADD){
                    buildCandidateAdvised(beanFactory,advisor,bean,beanDefinitionName);
                }
                continue;
            }
            Advised advisedBean = (Advised) bean;
            boolean isFindMatchAdvised = findMatchAdvised(advisedBean.getClass(),pointcut);
            if(operateEventEnum == OperateEventEnum.DEL){
                if(isFindMatchAdvised){
                    advisedBean.removeAdvice(advisor.getAdvice());
                    log.info("########################################## Remove Advice -->【{}】 For Bean -->【{}】 SUCCESS !",advisor.getAdvice().getClass().getName(),bean.getClass().getName());
                }
            }else if(operateEventEnum == OperateEventEnum.ADD){
                if(isFindMatchAdvised){
                    advisedBean.addAdvice(advisor.getAdvice());
                    log.info("########################################## Add Advice -->【{}】 For Bean -->【{}】 SUCCESS !",advisor.getAdvice().getClass().getName(),bean.getClass().getName());
                }
            }


        }
    }

    private static void buildCandidateAdvised(DefaultListableBeanFactory beanFactory,AspectJExpressionPointcutAdvisor advisor,Object bean,String beanDefinitionName) {
        AspectJExpressionPointcut pointcut = (AspectJExpressionPointcut) advisor.getPointcut();
        boolean isFindMatchCandidateAdvised = findMatchCandidateAdvised(bean.getClass(),pointcut);
        if(isFindMatchCandidateAdvised){
            ProxyFactory proxyFactory = new ProxyFactory();
            proxyFactory.setTarget(bean);
            proxyFactory.setProxyTargetClass(true);
            proxyFactory.addAdvisor(advisor);
            beanFactory.destroySingleton(beanDefinitionName);
            beanFactory.registerSingleton(beanDefinitionName,proxyFactory.getProxy());
            log.info("########################################## BuildCandidateAdvised -->【{}】 With Advice -->【{}】 SUCCESS !",bean.getClass().getName(),advisor.getAdvice().getClass().getName());
        }
    }

    public static Boolean findMatchAdvised(Class<?> targetClass, Pointcut pointcut){
        for (Method method : targetClass.getMethods()) {
            if(pointcut.getMethodMatcher().matches(method,targetClass)){
                return true;
            }
        }
        return false;
    }

    public static Boolean findMatchCandidateAdvised(Class<?> targetClass,Pointcut pointcut){
        return findMatchAdvised(targetClass,pointcut);
    }





}
