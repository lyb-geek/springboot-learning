package com.github.lybgeek.echo;


import org.pf4j.ExtensionPoint;

public interface EchoService extends ExtensionPoint {

    String echo(String msg);
}
