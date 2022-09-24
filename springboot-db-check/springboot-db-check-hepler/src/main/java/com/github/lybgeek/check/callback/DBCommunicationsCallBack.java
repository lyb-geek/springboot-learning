package com.github.lybgeek.check.callback;

import org.springframework.core.Ordered;

import java.sql.Connection;
import java.sql.SQLException;


public interface DBCommunicationsCallBack extends Ordered {

    void executeIfCommunicationsHealth(Connection conn);

    void executeIfCommunicationsUnHealth(SQLException e);

    @Override
    default int getOrder(){
        return 0;
    }
}
