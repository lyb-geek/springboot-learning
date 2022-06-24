package com.github.lybgeek.pipeline.spring.annotation;


import com.github.lybgeek.pipeline.spring.registrar.PipelineImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(PipelineImportBeanDefinitionRegistrar.class)
public @interface EnabledPipeline {

    String[] basePackages() default {};
}
