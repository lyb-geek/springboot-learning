package com.github.lybgeek.mybatisplus.extension.crud;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 重写update(Wrapper<T> updateWrapper), 更新时自动填充不生效问题
 * @author: lyb-geek
 * @date : 2020/09/12 9:06
 **/
@Aspect
@Component
@Slf4j
public class UpdateWapperAspect implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private  Map<String,Object> entityMap = new HashMap<>();

    @Pointcut("execution(* com.baomidou.mybatisplus.extension.service.IService.update(com.baomidou.mybatisplus.core.conditions.Wrapper))")
    public void pointcut(){

    }

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint pjp){
        Object updateEnityResult = this.updateEntity(pjp);
        if(ObjectUtils.isEmpty(updateEnityResult)){
            try {
                return pjp.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return updateEnityResult;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     *重写update(Wrapper<T> updateWrapper), 更新时自动填充不生效问题
     * @param pjp
     * @return
     */
    private Object updateEntity(ProceedingJoinPoint pjp){
        Object[] args = pjp.getArgs();
        if(args != null && args.length == 1){
            Object arg = args[0];
            if(arg instanceof Wrapper){
                Wrapper updateWrapper = (Wrapper)arg;
                Object entity = updateWrapper.getEntity();
                IService service = (IService) applicationContext.getBean(pjp.getTarget().getClass());
                if(ObjectUtils.isEmpty(entity)){
                    entity = entityMap.get(pjp.getTarget().getClass().getName());
                    if(ObjectUtils.isEmpty(entity)){
                        Class entityClz = ReflectionKit.getSuperClassGenericType(pjp.getTarget().getClass(), 1);
                        try {
                            entity = entityClz.newInstance();
                        } catch (InstantiationException e) {
                            log.warn("Entity instantiating exception!");
                        } catch (IllegalAccessException e) {
                            log.warn("Entity illegal access exception!");
                        }
                        entityMap.put(pjp.getTarget().getClass().getName(),entity);
                    }

                }
                return service.update(entity,updateWrapper);
            }
        }

        return null;

    }
}
