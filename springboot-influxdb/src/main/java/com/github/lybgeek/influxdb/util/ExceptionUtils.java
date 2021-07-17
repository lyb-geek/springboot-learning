package com.github.lybgeek.influxdb.util;


import com.github.lybgeek.influxdb.exception.InfluxdbException;

/**
 * 异常工具辅助类
 *
 *
 */
public final class ExceptionUtils {

    private ExceptionUtils() {
    }

    /**
     * 返回一个新的异常，统一构建，方便统一处理
     *
     * @param msg 消息
     * @param t   异常信息
     * @return 返回异常
     */
    public static InfluxdbException cause(String msg, Throwable t, Object... params) {
        return new InfluxdbException(StringUtils.format(msg, params), t);
    }

    /**
     * 重载的方法
     *
     * @param msg 消息
     * @return 返回异常
     */
    public static InfluxdbException cause(String msg, Object... params) {
        return new InfluxdbException(StringUtils.format(msg, params));
    }

    /**
     * 重载的方法
     *
     * @param t 异常
     * @return 返回异常
     */
    public static InfluxdbException cause(Throwable t) {
        return new InfluxdbException(t);
    }

}
