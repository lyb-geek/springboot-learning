package com.github.lybgeek.autoid.annotation;

import java.lang.annotation.*;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoId {
    /**
     * 主键名
     * @return
     */
    String primaryKey();

    /**
     * 支持的主键算法类型
     * @return
     */
    IdType type() default IdType.SNOWFLAKE;

    enum IdType{
        SNOWFLAKE
    }
}
