package com.github.lybgeek.transactional.annotation;

import java.lang.annotation.*;

/**
 * @description: 事务提交注解
 *
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AfterCommitTransationCallBack {


}
