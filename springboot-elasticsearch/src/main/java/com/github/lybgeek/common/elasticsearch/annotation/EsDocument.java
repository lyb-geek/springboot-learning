package com.github.lybgeek.common.elasticsearch.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.elasticsearch.index.VersionType;
import org.springframework.data.annotation.Persistent;

@Persistent
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EsDocument {
  String indexName();

  String type() default "_doc";

  boolean useServerConfiguration() default false;

  short shards() default 5;

  short replicas() default 1;

  String refreshInterval() default "1s";

  String indexStoreType() default "fs";

  boolean createIndex() default true;

  VersionType versionType() default VersionType.EXTERNAL;
}
