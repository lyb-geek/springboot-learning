package com.github.lybgeek.command;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static com.github.lybgeek.version.util.VersionNoUtils.reportVersion2RemoteSvc;

@Component
public class VersionFetchCommandLineRunner implements CommandLineRunner {

    ExecutorService executorService = Executors.newSingleThreadExecutor(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r,"report-version-thread");
        }
    });
    @Override
    public void run(String... args) throws Exception {
        executorService.execute(()->{
            reportVersion2RemoteSvc("http://localhost:8080/version/report","10000",Thread.currentThread().getContextClassLoader(), "org.apache.catalina.startup.Tomcat,org.springframework.beans.factory.BeanFactory");
        });

    }
}
