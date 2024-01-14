package com.github.lybgeek.log;



public class Log4jService implements LogService {
    @Override
    public void info(String msg) {
        System.out.println(Log4jService.class.getName() + " info: " + msg);
    }
}
