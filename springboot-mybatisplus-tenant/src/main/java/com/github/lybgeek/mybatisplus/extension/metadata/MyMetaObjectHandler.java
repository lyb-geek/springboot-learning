package com.github.lybgeek.mybatisplus.extension.metadata;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

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
        this.setInsertFieldValByName( "createDate", new Date(),metaObject);
        this.setInsertFieldValByName("lastUpdateDate",  new Date(),metaObject);
        this.setInsertFieldValByName("createdBy", "lyb-geek",metaObject);
        this.setInsertFieldValByName( "lastUpdatedBy",  "lyb-geek",metaObject);
        this.setInsertFieldValByName( "deleteFlag",  0,metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.setUpdateFieldValByName( "lastUpdatedBy", "admin",metaObject);
        this.setUpdateFieldValByName("lastUpdateDate",  new Date(),metaObject);
    }
}