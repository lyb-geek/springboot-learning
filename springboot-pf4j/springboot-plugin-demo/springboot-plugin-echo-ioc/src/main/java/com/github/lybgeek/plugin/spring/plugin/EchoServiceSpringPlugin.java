package com.github.lybgeek.plugin.spring.plugin;


import com.github.lybgeek.plugin.spring.config.EchoConfig;
import org.pf4j.PluginWrapper;
import org.pf4j.spring.SpringPlugin;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class EchoServiceSpringPlugin extends SpringPlugin {
    public EchoServiceSpringPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void stop() {
        System.out.println("EchoServiceSpringPlugin stop...");
        super.stop();
    }

    @Override
    public void start() {
        System.out.println("EchoServiceSpringPlugin start...");
        super.start();
    }

    @Override
    public void delete() {
        System.out.println("EchoServiceSpringPlugin delete...");
        super.delete();
    }

    @Override
    protected ApplicationContext createApplicationContext() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.setClassLoader(getWrapper().getPluginClassLoader());
        annotationConfigApplicationContext.register(EchoConfig.class);
        annotationConfigApplicationContext.refresh();
        return annotationConfigApplicationContext;

    }
}
