package com.github.lybgeek.aop;


import com.github.lybgeek.aop.service.EchoService;

public class AspectjweaverMainTest {

    public static void main(String[] args) {
        EchoService echoService = new EchoService();
        echoService.echo("Aspectjweaver");
    }
}
