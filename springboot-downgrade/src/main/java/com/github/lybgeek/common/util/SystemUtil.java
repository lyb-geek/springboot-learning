package com.github.lybgeek.common.util;

import com.github.lybgeek.common.config.EnvConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SystemUtil {


  public static boolean isProdEnv(){
    EnvConfig envConfig = SpringContextUtil.getBean(EnvConfig.class);
    String env = envConfig.getProfie();
    log.info("current env->{}",env);
    return "prod".equalsIgnoreCase(env);
  }

}
