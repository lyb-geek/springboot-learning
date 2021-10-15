package com.github.lybgeek.spi.test.interceptor;


import com.github.lybgeek.interceptor.model.InterceptorChain;
import com.github.lybgeek.spi.test.interceptor.service.HelloServiceA;
import com.github.lybgeek.spi.test.interceptor.service.impl.HelloServiceAImpl;
import com.github.lybgeek.spi.test.interceptor.service.impl.HelloServiceCImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class InterceptorTest {

    private InterceptorChain chain;

    @Before
    public void before(){
        InterceptorA interceptorA = new InterceptorA();
        InterceptorB interceptorB = new InterceptorB();
        InterceptorC interceptorC = new InterceptorC();
        chain = new InterceptorChain();
        chain.addInterceptor(interceptorA).addInterceptor(interceptorB).addInterceptor(interceptorC);
    }


    @Test
    public void testInterceptor(){
        HelloServiceA helloServiceA = (HelloServiceA) chain.pluginAll(new HelloServiceAImpl());
        Assert.assertEquals("hello-->A",helloServiceA.hello());
    }

    @Test
    public void testIgnoreInterceptorB(){
        HelloServiceA helloServiceA = (HelloServiceA) chain.pluginAll(new HelloServiceCImpl());
        Assert.assertEquals("hello-->c",helloServiceA.hello());
    }


    @Test
    public void testIgnoreRemainInterceptorsIfCurInterceptorSkip(){
        HelloServiceA helloServiceA = (HelloServiceA) chain.pluginAll(new HelloServiceCImpl());
        Assert.assertEquals("hello-->c",helloServiceA.hello());
    }


}
