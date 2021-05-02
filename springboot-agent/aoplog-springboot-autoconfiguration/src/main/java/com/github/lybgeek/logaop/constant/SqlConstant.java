package com.github.lybgeek.logaop.constant;


public class SqlConstant {

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

}
