package com.github.lybgeek.desensitization.autoconfigure.condition;


import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Conditional({OnDesensitizedFileCondition.class})
@Documented
public @interface ConditionalOnMissingDesensitizedFile {
}
