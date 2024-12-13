package com.github.lybgeek.echo.test;


import com.github.lybgeek.echo.EchoService;
import com.github.lybgeek.plugin.PluginManagerWapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pf4j.DefaultPluginManager;

import java.util.List;

import static org.pf4j.DefaultPluginManager.PLUGINS_DIR_CONFIG_PROPERTY_NAME;

public class Pf4jWapperTest {

    private PluginManagerWapper pluginManager;



    @Before
    public void beforeTest(){
        System.setProperty(PLUGINS_DIR_CONFIG_PROPERTY_NAME,"E:\\springboot-pf4j\\pf4j-plugins");
        pluginManager = new PluginManagerWapper(new DefaultPluginManager());
        pluginManager.addPluginPath("http://localhost:8081/repository/maven-snapshots/com/github/lybgeek/springboot-plugin-echo-ioc/1.0-SNAPSHOT/springboot-plugin-echo-ioc-1.0-20241119.065148-1.jar","E:\\springboot-pf4j\\springboot-plugin-demo\\springboot-plugin-echo\\target\\springboot-plugin-echo-1.0-SNAPSHOT.jar");

    }


    @Test
    public void testEchoPlugin(){
        List<EchoService> extensions = pluginManager.getExtensions(EchoService.class,"echo-plugin");
        for (EchoService extension : extensions) {
            System.out.println(extension.echo("hello world"));
        }

    }




}
