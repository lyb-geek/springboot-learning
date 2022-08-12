package com.github.lybgeek.advisor.annotation;


import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InjectId {
}
