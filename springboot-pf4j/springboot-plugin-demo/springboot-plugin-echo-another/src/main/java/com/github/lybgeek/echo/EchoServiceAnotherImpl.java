package com.github.lybgeek.echo;


import org.pf4j.Extension;


@Extension
public class EchoServiceAnotherImpl implements EchoService{
    @Override
    public String echo(String msg) {
        System.out.println("this is another test.....");
        return "echo: other...." + msg;

    }
}
