package com.github.lybgeek.common.elasticsearch.repository.anntation;

import com.github.lybgeek.common.elasticsearch.repository.registrar.ElasticsearchRepositoryRegistrar;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(value= ElasticsearchRepositoryRegistrar.class)
public @interface EnableCustomElasticsearchRepositories {
    String[] basePackages() default {};
    Class<?>[] basePackageClasses() default {};
}
