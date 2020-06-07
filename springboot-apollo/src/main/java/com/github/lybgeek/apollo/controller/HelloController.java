package com.github.lybgeek.apollo.controller;

import com.github.lybgeek.apollo.annotation.Log;
import com.github.lybgeek.apollo.hystrix.constant.HystrixConstant;
import com.github.lybgeek.apollo.util.AnnotationUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${hello}")
    private String hello;


    @GetMapping(value = "/hello")
    @Log(value = "${log.sayHello}")
    @HystrixCommand(groupKey = "sayHelloGroup",commandKey = "sayHelloCommandKey",fallbackMethod = "sayHelloFallBack",threadPoolKey="sayHelloThreadPoolKey",
            commandProperties = {
             @HystrixProperty(name = HystrixConstant.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS,value = "${sayHello.timeout}")//指定多久超时,超时后执行降级
            },threadPoolProperties = {
            //并发请求数超过coreSize，则进行降级
            @HystrixProperty(name = "coreSize",value = "${sayHello.coreSize}"),

    })
    public String sayHello(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "hello:"+hello;
    }


    public String sayHelloFallBack(){
        System.out.println(111);
        return "sayHelloFallBack:"+hello;
    }

    @GetMapping(value = "/refresh")
    @Log(value = "${log.refresh}")
    @HystrixCommand(groupKey = "refreshGroup",commandKey = "refreshCommandKey",fallbackMethod = "refreshFallBack",
            commandProperties = {
                    @HystrixProperty(name = HystrixConstant.EXECUTION_ISOLATION_STRATEGY,value = "SEMAPHORE"),
                    @HystrixProperty(name = HystrixConstant.EXECUTION_ISOLATION_SEMAPHORE_MAXCONCURRENTREQUESTS,value = "5"),
            })
    public String refresh()throws Exception{
        Log log = HelloController.class.getMethod("sayHello").getAnnotation(Log.class);
        AnnotationUtils.setAnnotationValue(log,"value","refresh");
        return "refresh:"+log.value();
    }

    public String refreshFallBack()throws Exception{
        return "refreshFallBack";
    }

}
