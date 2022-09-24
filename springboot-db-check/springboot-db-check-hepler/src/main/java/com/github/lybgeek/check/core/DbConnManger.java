package com.github.lybgeek.check.core;


import com.github.lybgeek.check.util.JdbcUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RequiredArgsConstructor
public class DbConnManger implements InitializingBean {

    private final DataSourceProperties dataSourceProperties;

    private Connection conn = null;



    public Connection getConn() {
        if(conn == null){
            reConnection();
        }
        return conn;
    }

    public void closeConnection() {
      if(conn != null){
          JdbcUtils.close(conn);
          conn = null;
      }
    }


    /**
     * 重连
     */
    private void reConnection(){
        try {
            conn = DriverManager.getConnection(dataSourceProperties.getUrl(), dataSourceProperties.getUsername(), dataSourceProperties.getPassword());
        } catch (SQLException e) {

        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        conn = DriverManager.getConnection(dataSourceProperties.getUrl(), dataSourceProperties.getUsername(), dataSourceProperties.getPassword());
    }
}
