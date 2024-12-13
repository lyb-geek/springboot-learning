package com.github.lybgeek.echo;


import com.github.lybgeek.pf4j.annotation.AutoPluginConfig;
import org.pf4j.Extension;


@AutoPluginConfig(description = "apt service",version = "1.0.0",provider = "lybgeek",className = "com.github.lybgeek.echo.plugin.EchoAptServicePlugin")
@Extension
public class EchoAptServiceImpl implements EchoService{
    @Override
    public String echo(String msg) {
        System.out.println("this is a apt echo.....");
        return "echo: apt" + msg;
    }
}
