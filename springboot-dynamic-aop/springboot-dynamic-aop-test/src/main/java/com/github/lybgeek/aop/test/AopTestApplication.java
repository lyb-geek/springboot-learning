package com.github.lybgeek.aop.test;


import com.github.lybgeek.aop.core.model.ProxyMetaInfo;
import com.github.lybgeek.aop.core.plugin.AopPluginFactory;
import com.github.lybgeek.aop.core.util.AopUtil;
import com.github.lybgeek.aop.test.echo.service.EchoService;
import com.github.lybgeek.aop.test.hello.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AopTestApplication implements ApplicationRunner {

    @Autowired
    private HelloService helloService;

    @Autowired
    private EchoService echoService;

    @Autowired
    private AopPluginFactory aopPluginFactory;


    public static void main(String[] args) {
        SpringApplication.run(AopTestApplication.class);
    }



    @Override
    public void run(ApplicationArguments args) throws Exception {
//       testProxy();
//         testSpringProxy();


    }

    private void testProxy() {
        ProxyMetaInfo proxyMetaInfo = ProxyMetaInfo.builder()
                .proxyUrl("jar:file:F:/springboot-learning/springboot-dynamic-aop/springboot-aop-plugin/springboot-aop-plugin-log/target/springboot-aop-plugin-log-0.0.1-SNAPSHOT.jar!/")
                .proxyClassName("com.github.lybgeek.interceptor.LogMethodInterceptor")
                .pointcut("execution (* com.github.lybgeek.aop.test.hello.service.HelloService.*(..))")
                .target(helloService)
                .build();

        HelloService proxy = AopUtil.getProxy(proxyMetaInfo);
        proxy.sayHello("李四");
    }

    private void testSpringProxy(){
        System.out.println("************************ TEST HELLO SERVICE PROXY START*******************");
         helloService.sayHello("张三");
       // 卸载
//        aopPluginFactory.uninstallPlugin("costTimeHelloService");
        // 1、创建新切面
        ProxyMetaInfo proxyMetaInfo = ProxyMetaInfo.builder()
                .proxyUrl("jar:file:F:/springboot-learning/springboot-dynamic-aop/springboot-aop-plugin/springboot-aop-plugin-log/target/springboot-aop-plugin-log-0.0.1-SNAPSHOT.jar!/")
                .proxyClassName("com.github.lybgeek.interceptor.LogMethodInterceptor")
                .pointcut("execution (* com.github.lybgeek.aop.test.hello.service.HelloService.*(..))")
                .id("logHelloService")
                .build();
        // 2、安装新切面
        aopPluginFactory.installPlugin(proxyMetaInfo);
        //3、验证切面
        helloService.sayHello("log");
        //4、卸载切面
        aopPluginFactory.uninstallPlugin("logHelloService");
        helloService.sayHello("uninstall");
        System.out.println("************************ TEST HELLO SERVICE PROXY END*******************");

        System.out.println("************************ TEST ECHO SERVICE PROXY START*******************");

//        // 1、创建新切面
        ProxyMetaInfo timeProxyMetaInfo = ProxyMetaInfo.builder()
//                .proxyUrl("http://localhost:8081/repository/maven-snapshots/com/github/lybgeek/springboot-aop-plugin-timecost/1.0-SNAPSHOT/springboot-aop-plugin-timecost-1.0-20230824.024753-1.jar")
                .proxyUrl("jar:file:F:/springboot-learning/springboot-dynamic-aop/springboot-aop-plugin/springboot-aop-plugin-timecost/target/springboot-aop-plugin-timecost-0.0.1-SNAPSHOT.jar!/")
                .proxyClassName("com.github.lybgeek.interceptor.TimeCostMethodInterceptor")
                .pointcut("@annotation(com.github.lybgeek.aop.test.annotation.TimeCost)")
                .id("timeCostEchoService")
                .build();
        // 2、安装新切面
        aopPluginFactory.installPlugin(timeProxyMetaInfo);
        echoService.echo("李四");

        System.out.println("************************ TEST ECHO SERVICE PROXY END*******************");
    }
}
