package com.github.lybgeek.chain.annotation;


import com.github.lybgeek.chain.registrar.ChainRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ChainRegistrar.class)
public @interface EnableChain {



    /**
     * Base packages to scan for annotated components.
     * attribute.
     */
    String[] basePackages() default {};
}
