package com.github.lybgeek.influxdb.exception;

public class InfluxdbException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InfluxdbException(String message) {
        super(message);
    }

    public InfluxdbException(Throwable throwable) {
        super(throwable);
    }

    public InfluxdbException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
