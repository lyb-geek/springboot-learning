package com.github.lybgeek.autoid.model;

import lombok.Data;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;

import java.util.List;


@Data
public class BoundSqlHelper {

    private BoundSql boundSql;

    private String sql;

    private String primaryKey;

    private TypeHandler typeHandler;

    private Configuration configuration;

    private boolean isAleadyIncludePrimaryKey;

    //是否批量操作
    private boolean isInsertBatch;

    private List<String> primaryKeyList;
}
