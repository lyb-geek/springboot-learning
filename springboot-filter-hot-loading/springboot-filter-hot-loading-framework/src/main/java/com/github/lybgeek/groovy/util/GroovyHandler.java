package com.github.lybgeek.groovy.util;

import groovy.lang.GroovyClassLoader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
public class GroovyHandler {

    private Class<?> clazz;
    private Object instance;
    private Method method;
    private Constructor<?> constructor;

	//加载String类型的代码
    public GroovyHandler(String javasShell) {
        this.clazz = new GroovyClassLoader().parseClass(javasShell);
    }
    //加载文件类型的代码
    public GroovyHandler(File file) {
        try {
            this.clazz = new GroovyClassLoader().parseClass(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	//设置构造函数（如果代码里带有Class）
    public void setConstructor(Class<?>... parameterTypes) {
        try {
            this.constructor = this.clazz.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
	//设置构造函数的参数并生成实例对象
    public void setInstance(Object... constructorValue) {
        try {
            if(this.constructor == null){
                this.instance = this.clazz.newInstance();
            }else {
                this.instance = this.constructor.newInstance(constructorValue);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
	//设置要调用方法（如果有参数，带上参数类型）
    public void setMethod(String methodName, Class<?>... parameterTypes) {
        try {
            this.method = this.clazz.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
	//调用方法（如果有参数，带上参数值）
    public Object doInvoke(Object... args) {
        try {
            return this.method.invoke(this.instance, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


}
