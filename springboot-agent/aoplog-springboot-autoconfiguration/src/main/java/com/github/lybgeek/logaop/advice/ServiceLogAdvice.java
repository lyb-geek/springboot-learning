package com.github.lybgeek.logaop.advice;


import com.alibaba.fastjson.JSON;
import com.github.lybgeek.logaop.entity.ServiceLog;
import com.github.lybgeek.logaop.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

@Slf4j
public class ServiceLogAdvice implements MethodInterceptor {

    private LogService logService;

    public ServiceLogAdvice(LogService logService) {
        this.logService = logService;
    }

    @Override
    public Object invoke(MethodInvocation invocation)  {

        long start = System.currentTimeMillis();
        long costTime = 0L;
        String status = ServiceLog.SUCEESS;
        Object result = null;
        String respResult = null;
        try {
            // 原有函数执行
            result = invocation.proceed();
            respResult = JSON.toJSONString(result);
        } catch (Throwable e){
            log.error(e.getMessage(),e);
            status = ServiceLog.FAIL;
            respResult = e.getMessage();
        } finally{
            costTime = System.currentTimeMillis() - start;
            saveLog(invocation.getArguments(), invocation.getMethod(), costTime, status, respResult);
        }
        return result;

    }

    private void saveLog(Object[] args, Method method, long costTime, String status, String respResult) {
            ServiceLog serviceLog = ServiceLog.builder()
                                    .serviceName(method.getDeclaringClass().getName())
                                    .costTime(costTime)
                                    .methodName(method.getName())
                                    .status(status)
                                    .reqArgs(JSON.toJSONString(args))
                                    .respResult(respResult).build();
           logService.saveLog(serviceLog);
    }
}
