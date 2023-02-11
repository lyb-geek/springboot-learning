package com.github.lybgeek.aop;


import com.github.lybgeek.aop.service.EchoService;

public class AspectMavenPluginMainTest {

    public static void main(String[] args) {
      EchoService.echo("aspectMavenPlugin");
    }
}
