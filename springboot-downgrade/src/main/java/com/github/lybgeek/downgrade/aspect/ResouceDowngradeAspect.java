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


  @Around(value = "@annotation(resouceDowngrade)")
  public Object resouceDowngrade(ProceedingJoinPoint pjp, ResouceDowngrade resouceDowngrade)
      throws Throwable {

     return this.downgradeWithRateLimiter(pjp,resouceDowngrade);
  }

  //---------利用guava的ratelimter进行限流降级------------------------------------//

  private Map<String,RateLimiter> rateLimiterMap = new ConcurrentHashMap<>();

  //存放初始阈值
  private Map<String,Integer> orginMaxThresholdMap = new ConcurrentHashMap<>();

  private Object downgradeWithRateLimiter(ProceedingJoinPoint pjp, ResouceDowngrade resouceDowngrade)throws Throwable {

    String signature = pjp.getSignature().toLongString();
    int maxThreshold = resouceDowngrade.maxThreshold();

    log.info("maxThreshold-->{}",maxThreshold);
    //permitsPerSecond为每秒往令牌里放入几个令牌
    RateLimiter rateLimiter;
    if(!rateLimiterMap.containsKey(signature)){
      rateLimiter = RateLimiter.create(maxThreshold);
      rateLimiterMap.put(signature,rateLimiter);
    }

    rateLimiter = this.resetRateLimiterIfMaxThresholdChange(signature, maxThreshold);

    boolean isAllowPass = rateLimiter.tryAcquire();
    if (isAllowPass) {
      return pjp.proceed();
    }

    return this.fallback(pjp, resouceDowngrade);

  }

  /**
   * 阈值发生动态切换，此时则需要变更限流器阈值
   * @param signature
   * @param maxThreshold
   * @return
   */
  private RateLimiter resetRateLimiterIfMaxThresholdChange(String signature, int maxThreshold) {

    int orginMaxThreshold;
    RateLimiter rateLimiter = rateLimiterMap.get(signature);
    if(orginMaxThresholdMap.containsKey(signature)){
      orginMaxThreshold = orginMaxThresholdMap.get(signature);
      //说明发生阈值发生动态切换，此时则需要变更限流器
      if(orginMaxThreshold != maxThreshold){
        log.info("signature:{}-->orginMaxThreshold is :{},currentMaxThreshold is :{},rateLimiter maxThreshold will change to -->{}"
            ,signature,orginMaxThreshold,maxThreshold,maxThreshold);
        rateLimiter.setRate(maxThreshold);
      }
    }else{
      orginMaxThresholdMap.put(signature,maxThreshold);
    }
    return rateLimiter;
  }

  //----------普通计数器进行限流------------------------------------//
  private Map<String, AtomicInteger> counter = new ConcurrentHashMap<>();
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

  /**
   * 限流回退逻辑
   * @param pjp
   * @param resouceDowngrade
   * @return
   */
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




}
