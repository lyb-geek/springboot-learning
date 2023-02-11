package com.github.lybgeek.log.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LogDTO implements Serializable {

    private String methodName;

    private String className;

    private Long costTime;

    private List<Object> args;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Long getCostTime() {
        return costTime;
    }

    public void setCostTime(Long costTime) {
        this.costTime = costTime;
    }

    public List<Object> getArgs() {
        return args;
    }

    public void setArgs(List<Object> args) {
        this.args = args;
    }


    @Override
    public String toString() {
        return "LogDTO{" +
                "methodName='" + methodName + '\'' +
                ", className='" + className + '\'' +
                ", costTime=" + costTime +
                ", args=" + args +
                '}';
    }
}
