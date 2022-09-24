package com.github.lybgeek.check.core.valid;

import java.sql.Connection;
import java.sql.SQLException;

public interface ValidConnectionChecker {

    boolean isValidConnection(Connection c, String query, int validationQueryTimeout) throws SQLException;
    
    default boolean isMysql(){
        return true;
    }
}
