package com.github.lybgeek.task;

import com.github.lybgeek.model.JdbcConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PrintJdbcConfigTask {

    @Autowired
    private JdbcConfig jdbcConfig;

    @Scheduled(cron = "0/5 * * * * *")
    public void print(){
        try {
            log.info("-------jdbcConfig------：{}",jdbcConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
