package com.github.lybgeek.event;

import com.github.lybgeek.model.JdbcConfig;
import com.github.lybgeek.util.CacheJdbcConfigUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JdbcConfigEvent {

    @EventListener
    public void receiveEvent(JdbcConfig jdbcConfig){
      log.info("refresh {}",jdbcConfig);
      JdbcConfig config = CacheJdbcConfigUtil.INSTANCE.refreshAndGet(jdbcConfig);
      System.out.println(config);
    }

    @EventListener
    public void receiveEvent(String msg){
        log.info("refresh {}",msg);
    }
}
