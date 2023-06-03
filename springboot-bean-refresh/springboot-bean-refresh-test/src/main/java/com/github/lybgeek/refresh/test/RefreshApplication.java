package com.github.lybgeek.refresh.test;


import com.github.lybgeek.refresh.test.monitor.util.ConfigFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class RefreshApplication implements ApplicationRunner {


    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        ConfigFileUtil.setConfig();
        SpringApplication.run(RefreshApplication.class);
    }



    @Override
    public void run(ApplicationArguments args) throws Exception {
        ConfigFileUtil.startFileMonitor(applicationContext);
        while(true){
            String name = applicationContext.getEnvironment().getProperty("test.name");
            System.out.println("name:"+name);
            TimeUnit.SECONDS.sleep(5);
        }
    }
}
