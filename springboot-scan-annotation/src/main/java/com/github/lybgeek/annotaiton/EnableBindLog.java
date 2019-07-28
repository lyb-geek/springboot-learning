package com.github.lybgeek.annotaiton;

import com.github.lybgeek.registrar.BindScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(value= BindScannerRegistrar.class)
public @interface EnableBindLog {
    String[] basePackages() default {};
    Class<?>[] basePackageClasses() default {};
}
