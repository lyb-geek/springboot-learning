package com.github.lybgeek.check.core.valid.support;

import com.github.lybgeek.check.core.valid.ValidConnectionChecker;
import com.github.lybgeek.check.properties.DbCheckProperies;
import com.github.lybgeek.check.util.JdbcUtils;
import com.github.lybgeek.check.util.MySqlUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ValidConnectionCheckerAdapter implements ValidConnectionChecker {

    private DbCheckProperies dbCheckProperies;

    public ValidConnectionCheckerAdapter(DbCheckProperies dbCheckProperies) {
        this.dbCheckProperies = dbCheckProperies;
    }

    @Override
    public boolean isValidConnection(Connection conn, String query, int validationQueryTimeout) throws SQLException {
        boolean valid = checkConnection(conn, query, validationQueryTimeout);

        // unexcepted branch
        if (valid && isMysql()) {
            long lastPacketReceivedTimeMs = MySqlUtils.getLastPacketReceivedTimeMs(conn);
            if (lastPacketReceivedTimeMs > 0) {
                long mysqlIdleMillis = System.currentTimeMillis() - lastPacketReceivedTimeMs;
                if (lastPacketReceivedTimeMs > 0
                        && mysqlIdleMillis >= dbCheckProperies.getTimeBetweenEvictionRunsMillis()) {
                    return false;
                }
            }
        }
        return valid;

    }


    private boolean checkConnection(Connection conn, String query, int validationQueryTimeout) throws SQLException {
        if (query == null || query.length() == 0) {
            return true;
        }

        if(conn == null){
            return false;
        }

        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            if (validationQueryTimeout > 0) {
                stmt.setQueryTimeout(validationQueryTimeout);
            }
            rs = stmt.executeQuery(query);
            return true;
        } finally {
            JdbcUtils.close(rs);
            JdbcUtils.close(stmt);
        }
    }





}
