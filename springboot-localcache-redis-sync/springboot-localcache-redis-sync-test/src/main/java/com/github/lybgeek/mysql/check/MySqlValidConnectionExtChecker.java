package com.github.lybgeek.mysql.check;


import com.alibaba.druid.pool.vendor.MySqlValidConnectionChecker;

import java.sql.Connection;
import java.util.concurrent.atomic.AtomicBoolean;

public class MySqlValidConnectionExtChecker extends MySqlValidConnectionChecker {
    public static AtomicBoolean isMySQLCommunicationsException = new AtomicBoolean();

    @Override
    public boolean isValidConnection(Connection conn, String query, int validationQueryTimeout) throws Exception {
        boolean validConnection = super.isValidConnection(conn, query, validationQueryTimeout);
        System.out.println("MySqlValidConnectionExtChecker...............");
        if(validConnection){
            boolean b = isMySQLCommunicationsException.compareAndSet(true, false);
            if(b){
                System.out.println("MYSQL正常。。。");
            }
        }else{
            System.err.println("MYSQL连接异常。。。");
            isMySQLCommunicationsException.compareAndSet(false,true);
        }

        return validConnection;

    }
}
