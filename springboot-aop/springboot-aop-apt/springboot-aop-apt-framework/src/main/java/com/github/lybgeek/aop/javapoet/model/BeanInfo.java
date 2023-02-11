package com.github.lybgeek.aop.javapoet.model;


import java.util.Map;

public class BeanInfo {

    private String packageName;

    private String className;

    private String simpleClassName;

    private String methodName;

    /**
     * key为参数名称
     * value为参数类型名称
     */
    private Map<String,String> parameterInfos;

    /**
     * 返回值参数类型名称
     */
    private String returnClassTypeName;


    public String getPackageName() {
        return packageName;
    }

    public BeanInfo setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public BeanInfo setClassName(String className) {
        this.className = className;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public BeanInfo setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public Map<String, String> getParameterInfos() {
        return parameterInfos;
    }

    public BeanInfo setParameterInfos(Map<String, String> parameterInfos) {
        this.parameterInfos = parameterInfos;
        return this;
    }

    public String getReturnClassTypeName() {
        return returnClassTypeName;
    }

    public BeanInfo setReturnClassTypeName(String returnClassTypeName) {
        this.returnClassTypeName = returnClassTypeName;
        return this;
    }

    public String getSimpleClassName() {
        return simpleClassName;
    }

    public BeanInfo setSimpleClassName(String simpleClassName) {
        this.simpleClassName = simpleClassName;
        return this;
    }

    public static BeanInfo buider(){
        return new BeanInfo();
    }
}
