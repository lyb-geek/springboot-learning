package com.github.lybgeek.echo;


import com.github.lybgeek.plugin.PluginManagerWapper;
import org.pf4j.PluginManager;

import java.util.List;

public class EchoTest {
    public static void main(String[] args) throws Exception {
        PluginManager pluginManager = new PluginManagerWapper("http://localhost:8081/repository/maven-snapshots/com/github/lybgeek/springboot-plugin-echo/1.0-SNAPSHOT/springboot-plugin-echo-1.0-20241105.024444-1.jar");
        List<EchoService> echoServices = pluginManager.getExtensions(EchoService.class);
        for (EchoService echoService : echoServices) {
            System.out.println(echoService.echo("test"));
        }

    }
}
