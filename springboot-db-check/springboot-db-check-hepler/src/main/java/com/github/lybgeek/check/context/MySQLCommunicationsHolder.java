package com.github.lybgeek.check.context;


import java.util.concurrent.atomic.AtomicBoolean;

public final class MySQLCommunicationsHolder {

    public static final AtomicBoolean isMySQLCommunicationsException = new AtomicBoolean();

    public static boolean isMySQLCommunicationsException() {
        return isMySQLCommunicationsException.get();
    }
}
