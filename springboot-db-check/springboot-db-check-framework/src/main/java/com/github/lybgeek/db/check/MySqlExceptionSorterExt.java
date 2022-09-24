package com.github.lybgeek.db.check;


import com.alibaba.druid.pool.vendor.MySqlExceptionSorter;
import com.github.lybgeek.db.check.context.MySQLCommunicationsHolder;
import com.github.lybgeek.db.check.event.CommunicationsUnHealthEvent;
import org.springframework.context.ApplicationContext;

import java.sql.SQLException;

public class MySqlExceptionSorterExt extends MySqlExceptionSorter {

    private ApplicationContext applicationContext;

    /**
     * 出现不可恢复异常
     * @param e
     * @return
     */
    @Override
    public boolean isExceptionFatal(SQLException e) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>MySqlExceptionSorterExt...................");
        boolean exceptionFatal = super.isExceptionFatal(e);
        MySQLCommunicationsHolder.isMySQLCommunicationsException.compareAndSet(false, true);
        CommunicationsUnHealthEvent event = CommunicationsUnHealthEvent.builder().sqlException(e).build();
        applicationContext.publishEvent(event);
        return exceptionFatal;

    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
