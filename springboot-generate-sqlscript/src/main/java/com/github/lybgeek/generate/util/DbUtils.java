package com.github.lybgeek.generate.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.db.DbUtil;
import com.github.lybgeek.generate.properties.DataSouceProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum DbUtils {

    INSTANCE;

    private Connection connection;

    public Connection getConnection(){
        try {
            Class.forName(DataSouceProperties.getDriverClassName());
            connection = DriverManager.getConnection(DataSouceProperties.getUrl(),DataSouceProperties.getUserName(),DataSouceProperties.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

    public void close(){
        if(ObjectUtil.isNotNull(connection)){
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * 获取数据库下的所有数据库表名
     *
     * @return Map<String, List<String>> key为数据库名称，value为该数据库下的所有表名
     */
    public Map<String, List<String>> getDbTablesNamesMap(){
        Map<String, List<String>> dbTableNamesMap = new HashMap<>();
        try {
            //获取数据库的元数据
            DatabaseMetaData dbMetaData = getConnection().getMetaData();
            //从元数据中获取到所有的表名
            ResultSet rs = dbMetaData.getTables(null, null, null,new String[] { "TABLE" });
            List<String> tableNames;
            while(rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                String curTableDbName = rs.getString("TABLE_CAT");
                String tableNameType = rs.getString("TABLE_TYPE");
                //表模式（可能为空）,在oracle中获取的是命名空间
                String tableNameSchema = rs.getString("TABLE_SCHEM");
                String tableNameRemark = rs.getString("REMARKS");
                System.out.println("表名: " + tableName + "，表所属数据库: " + curTableDbName + "，表类型: " + tableNameType + "，表模式: " + tableNameSchema + "，表备注: " + tableNameRemark);
                //跳过mysql自带的系统库
                if("sys".equalsIgnoreCase(curTableDbName)){
                    continue;
                }
                if(dbTableNamesMap.containsKey(curTableDbName)){
                    tableNames = dbTableNamesMap.get(curTableDbName);
                }else{
                    tableNames = new ArrayList<>();
                }
                tableNames.add(tableName);
                dbTableNamesMap.put(curTableDbName,tableNames);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        close();
        return dbTableNamesMap;
    }

    public static void main(String[] args) {
        Map<String, List<String>> dbTableNamesMap = DbUtils.INSTANCE.getDbTablesNamesMap();
        System.out.println(dbTableNamesMap);
    }

}
