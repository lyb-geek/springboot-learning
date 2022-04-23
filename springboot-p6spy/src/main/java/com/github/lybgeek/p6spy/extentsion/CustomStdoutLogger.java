package com.github.lybgeek.p6spy.extentsion;


public class CustomStdoutLogger extends com.p6spy.engine.spy.appender.StdoutLogger{

    @Override
    public void logText(String text) {
        System.out.println("sql:" + text);
    }
}
