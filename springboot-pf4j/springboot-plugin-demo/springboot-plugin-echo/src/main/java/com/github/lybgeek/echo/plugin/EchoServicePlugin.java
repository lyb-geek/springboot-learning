package com.github.lybgeek.echo.plugin;


import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

public class EchoServicePlugin extends Plugin {
    /**
     * Constructor to be used by plugin manager for plugin instantiation.
     * Your plugins have to provide constructor with this exact signature to
     * be successfully loaded by manager.
     *
     * @param wrapper
     */
    public EchoServicePlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>> EchoServicePlugin start");
    }

    @Override
    public void stop() {
        System.out.println(">>>>>>>>>>>>>>>>>>>> EchoServicePlugin stop");
    }

    @Override
    public void delete() {
        System.out.println("EchoServicePlugin delete");
    }
}
