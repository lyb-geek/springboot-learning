package com.github.lybgeek.proxy;

import com.github.lybgeek.annotaiton.BindLog;
import com.github.lybgeek.annotaiton.BingLogService;
import com.github.lybgeek.util.LogUtil;
import com.github.lybgeek.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Slf4j
public class JdkServiceProxy implements InvocationHandler {

    private Class<?> targetClz;

    public JdkServiceProxy(Class<?> targetClz){
        this.targetClz = targetClz;
    }

    public Object getInstance(){
        Object proxyObj = Proxy.newProxyInstance(JdkServiceProxy.class.getClassLoader(),targetClz.getInterfaces(),this);
        return proxyObj;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object obj = targetClz.newInstance();
            Field[] fields = targetClz.getDeclaredFields();
            if(fields != null && fields.length > 0){
                for(Field field : fields){
                    boolean hasAnnotation = field.isAnnotationPresent(Autowired.class);
                    if(hasAnnotation){
                        setField(obj, field);
                    }else{
                        hasAnnotation = field.isAnnotationPresent(Resource.class);
                        if(hasAnnotation){
                            setField(obj, field);
                        }
                    }
                }
            }
        boolean isAnnotation =  targetClz.isAnnotationPresent(BingLogService.class);
        Object result = method.invoke(obj,args);
        if(isAnnotation){
            BingLogService bingLogService = targetClz.getAnnotation(BingLogService.class);
            boolean logEnabled = bingLogService.value();
            if(logEnabled){
                //method是接口的方法，而非target本身的方法，@BindLog是注解在target上的方法
                String methodName = method.getName();
                Method targetMethod = targetClz.getMethod(methodName,method.getParameterTypes());
                boolean methodLogEnabledAnnotation = targetMethod.isAnnotationPresent(BindLog.class);
                if(methodLogEnabledAnnotation){
                    BindLog bindLog = targetMethod.getAnnotation(BindLog.class);
                    boolean methodLogEnabled = bindLog.value();
                    if(methodLogEnabled){
                        LogUtil.INSTACNE.saveLog(methodName, args, result);
                    }
                }else{
                    LogUtil.INSTACNE.saveLog(methodName, args, result);
                }

            }

        }
        return result;
    }

    private void setField(Object obj, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(obj, SpringContextUtil.getBean(field.getType()));
    }
}
