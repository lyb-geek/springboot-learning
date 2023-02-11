package com.github.lybgeek.aop.annotation;


import java.lang.annotation.*;

@Target(value = {ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface CostTimeRecoder {
}
