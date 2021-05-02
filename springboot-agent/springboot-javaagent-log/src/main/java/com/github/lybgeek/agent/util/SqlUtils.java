package com.github.lybgeek.agent.util;


import com.github.lybgeek.agent.properties.DbPropeties;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.DriverManager;

public enum SqlUtils {

    INSTANCE;

    private Log log = LogFactory.getLog(SqlUtils.class);

    private DbPropeties dbPropeties;

    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();


    public SqlUtils build(DbPropeties dbPropeties){
        this.dbPropeties = dbPropeties;
        return this;
    }


    public Connection getConn() {
        if(threadLocal.get() == null){
            Connection conn = null;
            try {
                Class.forName(dbPropeties.getDriverClassName());
                conn = DriverManager.getConnection(dbPropeties.getUrl(), dbPropeties.getUserName(), dbPropeties.getPassword());
                threadLocal.set(conn);
            } catch (Exception e) {
                log.error("获取连接失败：" + e.getMessage(),e);
            }
            return conn;
        }

        return threadLocal.get();
    }

    public void closeConn(Connection conn) {
        if(conn != null){
            DbUtils.closeQuietly(conn);
            threadLocal.remove();
        }
    }


}
