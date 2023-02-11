package com.github.lybgeek.aop.test;


import com.github.lybgeek.aop.service.EchoService;
import com.github.lybgeek.aop.test.service.HelloService;

public class AopAptMainTest {

    public static void main(String[] args) {
        System.out.println(new HelloService().sayHello("zhangsan"));
        new EchoService().echo("AST");
    }
}
