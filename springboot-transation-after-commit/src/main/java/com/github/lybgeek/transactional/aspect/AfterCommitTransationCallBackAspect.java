package com.github.lybgeek.transactional.aspect;



import com.github.lybgeek.transactional.annotation.AfterCommitTransationCallBack;
import com.github.lybgeek.transactional.process.AfterCommitTransationCallBackProcess;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 提交事务切面

 **/
@Aspect
@Component
@Slf4j
public class AfterCommitTransationCallBackAspect {

    @Autowired
    private AfterCommitTransationCallBackProcess afterCommitTransationProcess;


    @Around("@annotation(afterCommitTrantional)")
    public Object around(ProceedingJoinPoint pjp, AfterCommitTransationCallBack afterCommitTrantional){
        afterCommitTransationProcess.execute(()->{
            try {
                pjp.proceed();
            } catch (Throwable throwable) {
                log.error("sumbit thread error:"+throwable.getMessage(),throwable);
            }
        });

        return null;

    };




}
