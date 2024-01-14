package com.github.lybgeek.log;



public class Log4j2Service implements LogService {
    @Override
    public void info(String msg) {
        System.out.println(Log4j2Service.class.getName() + " info: " + msg);
    }
}
