package com.github.lybgeek.db.check.test.user.check;

import com.alibaba.druid.pool.ValidConnectionCheckerAdapter;
import com.github.lybgeek.check.context.MySQLCommunicationsHolder;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;


@Slf4j
public class MysqlCustomConnectionCheck extends ValidConnectionCheckerAdapter {


    @Override
    public boolean isValidConnection(Connection conn, String validateQuery, int validationQueryTimeout) throws Exception {
      return checkMySQLCommunications(conn,validateQuery,validationQueryTimeout);

    }


    private boolean checkMySQLCommunications (Connection conn, String validateQuery, int validationQueryTimeout)throws Exception {
        System.out.println("MysqlCustomConnectionCheck...................");
        boolean validConnection = super.isValidConnection(conn, validateQuery, validationQueryTimeout);
        if(validConnection){
            boolean b = MySQLCommunicationsHolder.isMySQLCommunicationsException.compareAndSet(true, false);
            if(b){
                System.out.println("数据库正常。。");
            }
        }

        return validConnection;
    }
}
