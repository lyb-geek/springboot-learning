package com.github.lybgeek.mongodb.common.id.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.data.mongodb.core.mapping.Document;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Document
public @interface GeneratedValue {

}
