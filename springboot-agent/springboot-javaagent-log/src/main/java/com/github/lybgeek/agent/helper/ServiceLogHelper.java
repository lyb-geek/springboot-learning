package com.github.lybgeek.agent.helper;


import com.github.lybgeek.agent.model.ServiceLog;
import com.github.lybgeek.agent.util.SqlUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public enum  ServiceLogHelper {

    INSTACNE;

    private Log log = LogFactory.getLog(ServiceLogHelper.class);

    private SqlUtils sqlUtils;


    private ExecutorService executorService = Executors.newFixedThreadPool(10,new ThreadFactory() {
        private AtomicInteger atomicInteger = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("svcLog-pool-" + atomicInteger.getAndIncrement());
            return thread;
        }
    });

    public static String CREATE_TABLE_SQL = "CREATE TABLE `service_log` (\n" +
            "  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',\n" +
            "  `service_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '服务名称',\n" +
            "  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '服务方法名',\n" +
            "  `req_args` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '服务请求参数',\n" +
            "  `resp_result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '服务响应参数',\n" +
            "  `cost_time` bigint DEFAULT NULL COMMENT '请求响应耗时，单位毫秒',\n" +
            "  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '响应状态，0、成功;1、失败',\n" +
            "  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  KEY `idx_service_method` (`service_name`,`method_name`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;";


    public ServiceLogHelper build(SqlUtils sqlUtilss){
        this.sqlUtils = sqlUtilss;
        return this;
    }


    public void initTable(){
        if(existTable()){
           // log.warn("已经存在表service_log");
            return;
        }
        Connection conn = getSqlUtils().getConn();
        QueryRunner runner = new QueryRunner();
        try {
            runner.update(conn,CREATE_TABLE_SQL);
        } catch (SQLException e) {
            log.error("初始化表结构失败：" + e.getMessage(),e);
        }finally {
            getSqlUtils().closeConn(conn);
        }
    }


    private String getCurentDataSource(String url){
        String curDataSource = null;
        // url = "jdbc:mysql://localhost:3306/agent?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai";
         if(url.contains("?")){
             //先得到//localhost:3306/agent
             curDataSource = url.substring(url.indexOf("//"),url.indexOf("?"));
             curDataSource = curDataSource.substring(curDataSource.lastIndexOf("/")+1);
             return curDataSource;
         }else{
             //jdbc:mysql://localhost:3306/agent
             curDataSource = url.substring(url.lastIndexOf("/") + 1);

         }
         return curDataSource;
    }

    private String getCurentDataSource(){
        Connection conn = getSqlUtils().getConn();
        QueryRunner runner = new QueryRunner();
        String sql = "SELECT DATABASE()";
        try {
           return runner.query(conn, sql, resultSet -> {
               while(resultSet.next()){
                   return resultSet.getString(1);
               }
               return null;
           });
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            getSqlUtils().closeConn(conn);
        }
        return null;
    }


    private boolean existTable(){
        Connection conn = getSqlUtils().getConn();
        String tableName = "service_log";
        ResultSet metaTables = null;
        try {
            DatabaseMetaData meta = conn.getMetaData();
            String url = meta.getURL();
            String currentDataSource = getCurentDataSource(url);
            metaTables =  meta.getTables(currentDataSource,null,tableName,new String[]{"TABLE"});
            if (metaTables.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(metaTables != null){
                try {
                    metaTables.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            getSqlUtils().closeConn(conn);

        }
        return false;
    }

    public void saveLog(ServiceLog serviceLog){
        executorService.submit(() -> {
            Connection conn = getSqlUtils().getConn();
            QueryRunner runner = new QueryRunner();
            try {
                String sql = "insert into service_log (service_name,method_name,req_args,resp_result,cost_time,status) values (?,?,?,?,?,?)";
                Object[] params = { serviceLog.getServiceName(), serviceLog.getMethodName(), serviceLog.getReqArgs(),serviceLog.getRespResult(),serviceLog.getCostTime(),serviceLog.getStatus() };
                runner.update(conn,sql,params);
                log.info("插入成功");
            } catch (SQLException e) {
                log.error("插入表失败：" + e.getMessage(),e);
            }finally {
                getSqlUtils().closeConn(conn);
            }
        });

    }

    public SqlUtils getSqlUtils() {
        return sqlUtils;
    }

    public void setSqlUtils(SqlUtils sqlUtils) {
        this.sqlUtils = sqlUtils;
    }


}
