package com.github.lybgeek.spi.test.spring;

import com.github.lybgeek.spi.extension.ExtensionLoader;
import com.github.lybgeek.spi.factory.ExtensionFactory;
import com.github.lybgeek.spring.spi.factory.SpringExtensionFactory;
import com.github.lybgeek.spi.test.spring.config.HelloServiceConfig;
import com.github.lybgeek.spi.test.spring.service.HelloService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @description:
 * @author: Linyb
 * @date : 2021/8/5 16:32
 **/
public class SpringSpiTest {

    private ExtensionFactory springExtensionFactory;

    @Before
    public void before(){
        springExtensionFactory = ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getActivate("spring");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(HelloServiceConfig.class);
        SpringExtensionFactory.addApplicationContext(context);
    }

    @Test
    public void testHelloCn(){
        HelloService helloService = springExtensionFactory.getExtension("helloCn",HelloService.class);
        String username = "张三";
        Assert.assertEquals("你好：" + username,helloService.sayHi(username));
    }

    @Test
    public void testHelloEn(){
        HelloService helloService = springExtensionFactory.getExtension("helloEn",HelloService.class);
        String username = "zhangsan";
        Assert.assertEquals("hello:" + username,helloService.sayHi(username));
    }
}
