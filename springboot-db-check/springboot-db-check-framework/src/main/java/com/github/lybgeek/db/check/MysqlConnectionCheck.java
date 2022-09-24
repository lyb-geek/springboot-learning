package com.github.lybgeek.db.check;

import com.alibaba.druid.pool.vendor.MySqlValidConnectionChecker;
import com.github.lybgeek.db.check.context.MySQLCommunicationsHolder;
import com.github.lybgeek.db.check.event.CommunicationsHealthEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.sql.Connection;


@Slf4j
public class MysqlConnectionCheck extends MySqlValidConnectionChecker {

    private ApplicationContext applicationContext;

    public MysqlConnectionCheck(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    @Override
    public boolean isValidConnection(Connection conn, String validateQuery, int validationQueryTimeout) throws Exception {
      return checkMySQLCommunications(conn,validateQuery,validationQueryTimeout);

    }

    private boolean checkMySQLCommunications (Connection conn, String validateQuery, int validationQueryTimeout) {
        boolean validConnection = false;
        try {
            validConnection = super.isValidConnection(conn, validateQuery, validationQueryTimeout);
        } catch (Exception e) {
        }
        if(validConnection){
            boolean b = MySQLCommunicationsHolder.isMySQLCommunicationsException.compareAndSet(true, false);
            if(b){
                CommunicationsHealthEvent event = CommunicationsHealthEvent.builder().conn(conn).build();
                applicationContext.publishEvent(event);
            }
        }


        return validConnection;
    }
}
