package com.github.lybgeek.echo.command;


import com.github.lybgeek.echo.EchoService;
import com.github.lybgeek.plugin.PluginManagerWapper;
import com.github.lybgeek.plugin.properties.PluginProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class Pf4jCommandLineRunner implements CommandLineRunner {

    private final PluginManagerWapper pluginManager;

    private final PluginProperties properties;
    @Override
    public void run(String... args) throws Exception {
        while (true){
            List<EchoService> extensions = pluginManager.getExtensions(EchoService.class);
            for (EchoService extension : extensions) {
                System.out.println(extension.echo("aaaa"));
                System.out.println("*******************************************");
            }
            TimeUnit.MILLISECONDS.sleep(200);
            System.out.println("===============================================");
        }


    }


    @Scheduled(initialDelay = 2000,fixedDelay = 1000)
    public void task(){
//        if(pluginManager.getPlugin("echo-spring-plugin") != null){
//            pluginManager.deletePlugin("echo-spring-plugin");
//        }

//        pluginManager.addPluginPath(properties.getDir() + "/" + "springboot-plugin-echo-ioc-1.0-SNAPSHOT.jar");
       
    }
}
