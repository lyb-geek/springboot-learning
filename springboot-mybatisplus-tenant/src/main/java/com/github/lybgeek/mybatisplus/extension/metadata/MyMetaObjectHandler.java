package com.github.lybgeek.mybatisplus.extension.metadata;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 填充器
 *
 *
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.setInsertFieldValByName( "createDate",  LocalDateTime.now(),metaObject);
        this.setInsertFieldValByName("lastUpdateDate",  LocalDateTime.now(),metaObject);
        this.setInsertFieldValByName("createdBy", "lyb-geek",metaObject);
        this.setInsertFieldValByName( "lastUpdatedBy",  "lyb-geek",metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.setUpdateFieldValByName( "lastUpdatedBy", "admin",metaObject);
        this.setUpdateFieldValByName("lastUpdateDate",  LocalDateTime.now(),metaObject);
    }
}