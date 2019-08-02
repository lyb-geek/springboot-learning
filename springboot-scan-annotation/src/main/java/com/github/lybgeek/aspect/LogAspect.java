package com.github.lybgeek.aspect;

import com.github.lybgeek.annotaiton.BindLog;
import com.github.lybgeek.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

//@Component
//@Aspect
@Slf4j
public class LogAspect {


    @Around(value="@annotation(bindLog)")
    public Object around(ProceedingJoinPoint pjp, BindLog bindLog){
        String method = pjp.getSignature().getName();
        Object[] params = pjp.getArgs();
        try {
            Object result = pjp.proceed(params);
            boolean logEnabled = bindLog.value();
            if(logEnabled){
                LogUtil.INSTACNE.saveLog(method, params, result);
            }
            return result;
        } catch (Throwable throwable) {
            log.error(throwable.getMessage(),throwable);
        }
        return null;

    }


}
