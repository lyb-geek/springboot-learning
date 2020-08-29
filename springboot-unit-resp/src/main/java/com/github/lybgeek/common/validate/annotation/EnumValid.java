package com.github.lybgeek.common.validate.annotation;


import com.github.lybgeek.common.validate.constraint.EnumConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {EnumConstraintValidator.class})
public @interface EnumValid {
 
    String message() default "";
 
    // 作用参考@Validated和@Valid的区别
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
 
    /**
     * 目标枚举类
     */
    Class<?> target() default Class.class;
 
    /**
     * 是否忽略空值
     */
    boolean ignoreEmpty() default true;
}
