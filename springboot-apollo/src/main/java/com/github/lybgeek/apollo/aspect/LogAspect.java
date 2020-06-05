package com.github.lybgeek.apollo.aspect;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.github.lybgeek.apollo.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @ApolloConfig
    private Config config;

    @Around(value = "@annotation(log)")
    public Object around(ProceedingJoinPoint pjp, Log log){
        String logValue = this.logValue(log);
        System.out.println(logValue);
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return null;

    }

    private String logValue(Log log){
        String key = StringUtils.deleteWhitespace(log.value().replace("${", "").replace("}", ""));
        AtomicReference<String> value = new AtomicReference<>(config.getProperty(key, null));
        config.addChangeListener((ConfigChangeEvent changeEvent)->{
            ConfigChange configChange = changeEvent.getChange(key);
            if(ObjectUtils.isNotEmpty(configChange)){
                System.out.println("change key:"+configChange.getPropertyName()+"---> oldValue:" + configChange.getOldValue()+", newValue:" + configChange.getNewValue());
                value.set(configChange.getNewValue());
            }
        });

        return value.get();
    }


}
