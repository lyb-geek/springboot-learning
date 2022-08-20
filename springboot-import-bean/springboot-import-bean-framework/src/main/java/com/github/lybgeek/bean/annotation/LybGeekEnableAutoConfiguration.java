package com.github.lybgeek.bean.annotation;


import com.github.lybgeek.bean.selector.LybGeekAutoConfigurationImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(LybGeekAutoConfigurationImportSelector.class)
@Documented
public @interface LybGeekEnableAutoConfiguration {
}
