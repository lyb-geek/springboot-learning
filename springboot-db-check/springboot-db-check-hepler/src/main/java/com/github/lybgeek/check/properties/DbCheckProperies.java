package com.github.lybgeek.check.properties;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = DbCheckProperies.PREFIX)
public class DbCheckProperies {

    public static final String PREFIX = "lybgeek.dbcheck";

    /**
     * 是否开启数据库检测.默认开启
     */
    private boolean enabled = true;

    /**
     * 配置间隔多久才进行一次检测.单位毫秒.默认500毫秒
     */
    private long timeBetweenEvictionRunsMillis = 500;

    /**
     * 验证查询超时时间.单位毫秒
     */
    private int validationQueryTimeout;

    /**
     * 检测连接是否有效 sql
     */
    private String validationQuery = "select 1";


}
