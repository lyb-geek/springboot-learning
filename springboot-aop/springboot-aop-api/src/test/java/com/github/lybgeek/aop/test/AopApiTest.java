package com.github.lybgeek.aop.test;


import com.github.lybgeek.aop.aspect.EchoAspect;
import com.github.lybgeek.aop.service.EchoService;
import org.junit.Test;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ProxyFactoryBean;

import java.util.Arrays;

public class AopApiTest {

    @Test
    public void testAopByAspectJProxyFactory(){
        AspectJProxyFactory aspectJProxyFactory = new AspectJProxyFactory(new EchoService());
        aspectJProxyFactory.addAspect(EchoAspect.class);
        EchoService echoService = aspectJProxyFactory.getProxy();
        echoService.echo("AspectJProxyFactory");
    }

    @Test
    public void testAopByProxyFactoryBean(){
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new EchoService());

        AspectJExpressionPointcutAdvisor aspectJExpressionPointcutAdvisor = new AspectJExpressionPointcutAdvisor();
        aspectJExpressionPointcutAdvisor.setExpression("execution(* com.github.lybgeek.aop.service.EchoService.echo(..))");
        aspectJExpressionPointcutAdvisor.setAdvice((MethodBeforeAdvice) (method, args, target) -> System.out.println("USE AOP BY ASPECT WITH ARGS: " + Arrays.toString(args)));
        proxyFactoryBean.addAdvisor(aspectJExpressionPointcutAdvisor);

        EchoService echoService = (EchoService) proxyFactoryBean.getObject();
        echoService.echo("ProxyFactoryBean");

    }

    @Test
    public void testAopByProxyFactory(){
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new EchoService());

        AspectJExpressionPointcutAdvisor aspectJExpressionPointcutAdvisor = new AspectJExpressionPointcutAdvisor();
        aspectJExpressionPointcutAdvisor.setExpression("execution(* com.github.lybgeek.aop.service.EchoService.echo(..))");
        aspectJExpressionPointcutAdvisor.setAdvice((MethodBeforeAdvice) (method, args, target) -> System.out.println("USE AOP BY ASPECT WITH ARGS: " + Arrays.toString(args)));
        proxyFactory.addAdvisor(aspectJExpressionPointcutAdvisor);

        EchoService echoService = (EchoService) proxyFactory.getProxy();
        echoService.echo("ProxyFactory");



    }



}
