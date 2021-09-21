package com.github.lybgeek.scan.annotation;

import com.github.lybgeek.scan.register.ThirdPartySvcRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ThirdPartySvcRegister.class)
public @interface EnableThirdPartySvc {
}
