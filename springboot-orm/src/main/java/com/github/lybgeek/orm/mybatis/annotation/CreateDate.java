package com.github.lybgeek.orm.mybatis.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Documented
public @interface CreateDate {

	String value() default "";
}