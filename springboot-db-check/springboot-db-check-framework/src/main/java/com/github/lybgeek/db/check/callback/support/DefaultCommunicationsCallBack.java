package com.github.lybgeek.db.check.callback.support;

import com.github.lybgeek.db.check.callback.DBCommunicationsCallBack;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;


@Slf4j
public class DefaultCommunicationsCallBack implements DBCommunicationsCallBack {


    @Override
    public void executeIfCommunicationsHealth(Connection conn) {
        if(log.isDebugEnabled()){
            log.debug("数据库连接正常。。。");
        }
    }

    @Override
    public void executeIfCommunicationsUnHealth(SQLException e) {
        log.error("数据库连接异常。。。");
    }


}
