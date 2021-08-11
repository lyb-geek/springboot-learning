package com.github.lybgeek.spi.framework.anotation;


import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface Spi {

    String defalutSpiImplClassName() default "";

}
