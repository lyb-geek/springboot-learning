package com.github.lybgeek.downgrade.aspect;

import com.github.lybgeek.common.util.SpringContextUtil;
import com.github.lybgeek.downgrade.annotation.ResouceDowngrade;
import com.google.common.util.concurrent.RateLimiter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@Aspect
public class ResouceDowngradeAspect {


  private Map<String, AtomicInteger> counter = new ConcurrentHashMap<>();


  @Around(value = "@annotation(resouceDowngrade)")
  public Object resouceDowngrade(ProceedingJoinPoint pjp, ResouceDowngrade resouceDowngrade)
      throws Throwable {

     return this.downgrade(pjp,resouceDowngrade);
  }


  private Object downgrade(ProceedingJoinPoint pjp, ResouceDowngrade resouceDowngrade)throws Throwable{

    String signature = pjp.getSignature().toLongString();
    int maxThreshold = resouceDowngrade.maxThreshold();
    log.info("maxThreshold-->{}",maxThreshold);
    if(counter.containsKey(signature)){
      if(counter.get(signature).incrementAndGet()> maxThreshold){
        log.info("signature:{},current requestCount:{},maxThreshold:{}",signature,counter.get(signature).get(),maxThreshold);
        return this.fallback(pjp, resouceDowngrade);
      }
    }else{
      counter.put(signature,new AtomicInteger(1));
    }


    //极端情况下，当maxThreshold为0
    if(counter.get(signature).get() > maxThreshold){
      log.info("signature:{},current requestCount:{},maxThreshold:{}",signature,counter.get(signature).get(),maxThreshold);
      return this.fallback(pjp, resouceDowngrade);
    }
    Object result = null;
    try {
      result = pjp.proceed();
    } catch (Throwable throwable) {
      throw throwable;
    } finally {
      counter.get(signature).decrementAndGet();
    }
    return result;
  }

  private Object fallback(ProceedingJoinPoint pjp, ResouceDowngrade resouceDowngrade) {

    String methodName = resouceDowngrade.fallbackMethod();
    if(StringUtils.isEmpty(methodName)){
      methodName = pjp.getSignature().getName();
    }
    try {
      MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
      Method method = SpringContextUtil.getBean(resouceDowngrade.fallbackClass()).getClass().getMethod(methodName,methodSignature.getParameterTypes());
      log.info("enter fallbackMethod:{}",method);
      return method.invoke(SpringContextUtil.getBean(resouceDowngrade.fallbackClass()),pjp.getArgs());
    } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      log.error("fallback error:"+e.getMessage(),e);
    }

    return null;
  }


  //---------利用guava的ratelimter进行限流降级------------------------------------//

  private RateLimiter rateLimiter = RateLimiter.create(1.0);//permitsPerSecond为每秒往令牌里放入几个令牌

  private Object downgradeWithRateLimiter(ProceedingJoinPoint pjp, ResouceDowngrade resouceDowngrade)throws Throwable {

    boolean isAllowPass = rateLimiter.tryAcquire();
    if (isAllowPass) {
      return pjp.proceed();
    }

    return this.fallback(pjp, resouceDowngrade);

  }

}
