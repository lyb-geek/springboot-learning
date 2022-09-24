package com.github.lybgeek.db.check.test.user.check;


import com.github.lybgeek.check.callback.DBCommunicationsCallBack;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class MysqlCommunicationsCallBack implements DBCommunicationsCallBack {
    @Override
    public void executeIfCommunicationsHealth(Connection conn) {
        System.out.println("MysqlCommunicationsCallBack..............executeIfCommunicationsHealth...............");
    }

    @Override
    public void executeIfCommunicationsUnHealth(SQLException e) {
        System.out.println("MysqlCommunicationsCallBack..............executeIfCommunicationsUnHealth...............");
    }
}
