package com.github.lybgeek.event;

import com.github.lybgeek.model.JdbcConfig;
import com.github.lybgeek.util.CacheJdbcConfigUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JdbcConfigEvent {

    @Autowired
    private JdbcConfig jdbcConfig;

    @EventListener
    @Async
    public void changeRegisterBean(JdbcConfig jdbcConfig){
      log.info("refresh {}",jdbcConfig);
      JdbcConfig config = CacheJdbcConfigUtil.INSTANCE.refreshAndGet(jdbcConfig);
      BeanUtils.copyProperties(config,this.jdbcConfig);
      System.out.println(config);
    }

    @EventListener
    public void receiveEvent(String msg){
        log.info("refresh {}",msg);
    }
}
