package com.github.lybgeek.feign.annotation;


import com.github.lybgeek.feign.config.FeignClientsServiceNameAppendEnvConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(FeignClientsServiceNameAppendEnvConfig.class)
public @interface EnableAppendEnv2FeignServiceName {


}
