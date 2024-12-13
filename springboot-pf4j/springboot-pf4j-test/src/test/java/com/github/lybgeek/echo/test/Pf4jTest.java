package com.github.lybgeek.echo.test;


import com.github.lybgeek.echo.EchoService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginManager;

import java.nio.file.Paths;
import java.util.List;


public class Pf4jTest {


    private PluginManager pluginManager;



    @Before
    public void beforeTest(){
       pluginManager = new DefaultPluginManager();
       pluginManager.loadPlugin(Paths.get("E:\\springboot-pf4j\\pf4j-plugins\\springboot-plugin-echo-1.0-SNAPSHOT.jar"));
       pluginManager.startPlugins();

    }


    @Test
    public void testEchoPlugin(){
        List<EchoService> extensions = pluginManager.getExtensions(EchoService.class,"echo-plugin");
        for (EchoService extension : extensions) {
            System.out.println(extension.echo("hello world"));
        }

    }


    @After
    public void afterTest(){
        pluginManager.deletePlugin("echo-plugin");
    }


}
