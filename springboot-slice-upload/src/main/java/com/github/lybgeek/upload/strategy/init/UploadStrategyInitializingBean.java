package com.github.lybgeek.upload.strategy.init;

import com.github.lybgeek.upload.strategy.context.UploadContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UploadStrategyInitializingBean implements InitializingBean {

  @Override
  public void afterPropertiesSet() throws Exception {
    log.info("init uploadStrategy ...");
    UploadContext.INSTANCE.init();
  }
}
