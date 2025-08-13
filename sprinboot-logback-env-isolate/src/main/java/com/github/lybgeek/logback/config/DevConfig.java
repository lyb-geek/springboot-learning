package com.github.lybgeek.logback.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("dev")
@Slf4j
@RequiredArgsConstructor
public class DevConfig implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {

        log.info("当前环境是DEV");

    }
}
