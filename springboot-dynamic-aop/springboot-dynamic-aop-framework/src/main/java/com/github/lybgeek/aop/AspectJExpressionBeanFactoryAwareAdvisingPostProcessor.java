package com.github.lybgeek.aop;


import lombok.RequiredArgsConstructor;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * 参考
 * @see MethodValidationPostProcessor 实现
 *
 */
@Deprecated
@RequiredArgsConstructor
public class AspectJExpressionBeanFactoryAwareAdvisingPostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor implements InitializingBean {

    private final AspectJExpressionPointcutAdvisor aspectJExpressionPointcutAdvisor;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.advisor = aspectJExpressionPointcutAdvisor;
    }

    public AspectJExpressionPointcutAdvisor getAspectJExpressionPointcutAdvisor() {
        return aspectJExpressionPointcutAdvisor;
    }
}
