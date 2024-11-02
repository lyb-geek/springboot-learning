package com.github.lybgeek.togglz.env.constant;


public interface TogglzConstant {
    String ENV_DEV = "env";
    String ENV_TEST = "test";
    String ENV_UAT = "uat";
    String ENV_PROD = "prod";

    String STORE_TYPE_INMEMORY = "inMemory";
    String STORE_TYPE_JDBC = "jdbc";
    String STORE_TYPE_FILE = "file";

    String PROXY_TYPE_KEY = "proxy-type";
    String PROXY_TYPE_AOP = "aop";
    String PROXY_TYPE_BYTEBUDDY = "bytebuddy";


    String DEFAULT_TOGGLZ_TABLE_NAME = "togglz";
}
