package com.github.lybgeek.echo;


import org.pf4j.Extension;

@Extension
public class EchoServiceImpl implements EchoService{
    @Override
    public String echo(String msg) {
        System.out.println("this is a test.....");
        return "echo:" + msg;
    }
}
