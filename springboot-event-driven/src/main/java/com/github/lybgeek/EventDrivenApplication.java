package com.github.lybgeek;

import com.github.lybgeek.model.JdbcConfig;
import com.github.lybgeek.util.CacheJdbcConfigUtil;
import com.github.lybgeek.watch.constant.Constants;
import com.github.lybgeek.watch.util.WatcherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EventDrivenApplication implements ApplicationRunner {

    @Autowired
    private JdbcConfig jdbcConfig;
    public static void main( String[] args ){
        SpringApplication.run(EventDrivenApplication.class,args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        CacheJdbcConfigUtil.INSTANCE.put(jdbcConfig);
        WatcherUtil.INSTANCE.monitorConfig(Constants.CONFIG_NAME);
    }
}
