package com.github.lybgeek.validate.constraint.anotation;

import com.github.lybgeek.validate.constraint.UniqueConstraintValidator;
import com.github.lybgeek.validate.constraint.service.UniqueCheckService;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = {UniqueConstraintValidator.class}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {

    String message() default "{javax.validation.constraints.Unique.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends UniqueCheckService> checkUniqueBeanClass();

    String checkField() default "";
}
