package com.github.lybgeek.config.binder.support;


import com.github.lybgeek.config.model.RefreshProperty;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggerPropertyRebinder extends AbstractPropertyRebinder {

    @Override
    public void binder(RefreshProperty refreshProperty) {
        log.info("refreshProperty:{}", refreshProperty);
    }
}
