package com.github.lybgeek.db.check.test.user.check;


import com.alibaba.druid.pool.vendor.MySqlExceptionSorter;

import com.github.lybgeek.check.context.MySQLCommunicationsHolder;
import org.springframework.context.ApplicationContext;

import java.sql.SQLException;

public class MySqlCustomExceptionSorter extends MySqlExceptionSorter {

    private ApplicationContext applicationContext;


    /**
     * 出现不可恢复异常
     * @param e
     * @return
     */
    @Override
    public boolean isExceptionFatal(SQLException e) {
        System.out.println("MySqlCustomExceptionSorter...................");
        boolean exceptionFatal = super.isExceptionFatal(e);
        MySQLCommunicationsHolder.isMySQLCommunicationsException.compareAndSet(false, true);
        return exceptionFatal;

    }


    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
