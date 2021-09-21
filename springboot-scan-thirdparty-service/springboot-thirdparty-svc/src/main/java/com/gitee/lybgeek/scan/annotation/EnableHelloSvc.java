package com.gitee.lybgeek.scan.annotation;


import com.gitee.lybgeek.scan.selector.HelloImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(HelloImportSelector.class)
//@Import(HelloServiceImpl.class)
public @interface EnableHelloSvc {
}
