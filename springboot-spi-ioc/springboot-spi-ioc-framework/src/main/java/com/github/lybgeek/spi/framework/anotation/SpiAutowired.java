package com.github.lybgeek.spi.framework.anotation;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
@Documented
public @interface SpiAutowired {


    @AliasFor(annotation = Qualifier.class)
    String value() default "";

}
