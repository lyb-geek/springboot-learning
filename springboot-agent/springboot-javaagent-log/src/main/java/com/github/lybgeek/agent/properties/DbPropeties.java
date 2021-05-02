package com.github.lybgeek.agent.properties;


public class DbPropeties {

    public static String DRIVER_CLASSNAME_KEY = "jdbc.driverClassName";
    public static String URL_KEY = "jdbc.url";
    public static String USER_NAME_KEY = "jdbc.userName";
    public static String PASSWORD_KEY = "jdbc.password";

    private String driverClassName = "com.mysql.cj.jdbc.Driver";

    private String url = "jdbc:mysql://localhost:3306/agent?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai";

    private String userName = "root";

    private String password = "123456";

    public DbPropeties(String driverClassName, String url, String userName, String password) {
        this.driverClassName = driverClassName;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    public DbPropeties(String url, String userName, String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    public DbPropeties() {
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "DbPropeties{" +
                "driverClassName='" + driverClassName + '\'' +
                ", url='" + url + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static void main(String[] args) {

    }
}
