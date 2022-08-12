package com.github.lybgeek.advisor;


import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;

public class CustomerPointcutAdvisor implements PointcutAdvisor {



    @Override
    public Pointcut getPointcut() {
        return new CustomerStaticMethodMatcherPointcut();
    }

    @Override
    public Advice getAdvice() {
        return new CustomerMethodInterceptor();
    }

    @Override
    public boolean isPerInstance() {
        return true;
    }
}
