package com.github.lybgeek.elasticsearch.annotation;

import com.github.lybgeek.elasticsearch.enu.OperateType;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface EsOperate {

  OperateType type();

  String indexName();

}
