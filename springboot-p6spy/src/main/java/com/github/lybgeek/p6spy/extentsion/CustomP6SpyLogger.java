package com.github.lybgeek.p6spy.extentsion;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

public class CustomP6SpyLogger implements MessageFormattingStrategy {

    /**
     * Sql日志格式化
     *
     * @param connectionId: 连接ID
     * @param now:          当前时间
     * @param elapsed:      花费时间
     * @param category:     类别
     * @param prepared:     预编译SQL
     * @param sql:          最终执行的SQL
     * @param url:          数据库连接地址
     * @return 格式化日志结果
     */
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return StringUtils.isNotEmpty(sql) ? " 耗时：" + elapsed + " ms " + now +
                "\n 执行 SQL：" + sql.replaceAll("[\\s]+", " ") + "\n" : "";
    }
}
