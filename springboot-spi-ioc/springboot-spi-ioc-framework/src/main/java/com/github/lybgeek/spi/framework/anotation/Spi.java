package com.github.lybgeek.spi.framework.anotation;


import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface Spi {

    @AliasFor(attribute = "value")
    String defalutSpiImplClassName() default "";

    @AliasFor(attribute = "defalutSpiImplClassName")
    String value() default "";

}
