package com.github.lybgeek.generate.properties;

import com.github.lybgeek.generate.util.YmlUtil;

public class DataSouceProperties {

    private static String driverClassName;

    private static String url;

    private static String userName;

    private static String password;


    static {
        driverClassName = YmlUtil.getValue("spring.datasource.driver-class-name").toString();
        url = YmlUtil.getValue("spring.datasource.url").toString();
        userName = YmlUtil.getValue("spring.datasource.username").toString();
        password = YmlUtil.getValue("spring.datasource.password").toString();
    }


    public static String getDriverClassName() {
        return driverClassName;
    }

    public static void setDriverClassName(String driverClassName) {
        DataSouceProperties.driverClassName = driverClassName;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        DataSouceProperties.url = url;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        DataSouceProperties.userName = userName;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        DataSouceProperties.password = password;
    }

    public static void main(String[] args) {
        System.out.println(DataSouceProperties.getDriverClassName());
        System.out.println(DataSouceProperties.getUrl());
        System.out.println(DataSouceProperties.getUserName());
        System.out.println(DataSouceProperties.getPassword());
    }
}
