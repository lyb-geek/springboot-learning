package com.github.lybgeek.spi.framework.anotation;


import com.github.lybgeek.spi.framework.spring.register.SpiRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Import(SpiRegister.class)
public @interface EnableSpi {

    String[] basePackages() default {};
}
