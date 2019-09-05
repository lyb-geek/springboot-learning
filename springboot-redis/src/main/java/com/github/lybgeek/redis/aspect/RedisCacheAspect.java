package com.github.lybgeek.redis.aspect;

import com.github.lybgeek.redis.annotation.RedisCache;
import com.github.lybgeek.redis.enu.CacheOperateType;
import com.github.lybgeek.redis.util.RedisUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
@Slf4j
public class RedisCacheAspect {

  private static  final String STR_SPILT = "_";

  @Autowired
  private RedisUtil redisUtil;



  @Around("@annotation(redisCache)")
  public Object around(ProceedingJoinPoint pjp, RedisCache redisCache){

    String mehtodName = pjp.getSignature().getName();
    StopWatch stopWatch = new StopWatch("redisCache");
    stopWatch.start(mehtodName);
    try {
      Object result = getResult(pjp, redisCache);
      stopWatch.stop();
      log.info("cost time:{}",stopWatch.prettyPrint());
      return result;
    } catch (Throwable throwable) {
      log.error(throwable.getMessage(),throwable);
    }


    return null;

  }


  private Object getResult(ProceedingJoinPoint pjp, RedisCache cache) throws Throwable{
    Object result = null;

    CacheOperateType type = cache.type();
    String key = getCacheKey(pjp,cache.cacheKeyPrefix());

    switch (type){
      case ADD:
        result = saveOrUpdate(pjp,key,cache.expireTime());
        log.info("add data to cache");
        break;
      case UPDTAE:
        result = saveOrUpdate(pjp,key,cache.expireTime());
        log.info("update data to cache");
        break;
      case DELETE:
        result = delete(pjp,cache);
        break;
      case QUERY:
        result = query(pjp,key,cache.expireTime());
        break;
        default:
          result = pjp.proceed();
          break;
    }

    return result;
  }

  /**
   * 构造缓存key，key的组成规则,如果cacheKeyPrefix不为空，则key为cacheKeyPrefix_参数列表，如果cacheKeyPrefix为空
   *  则key为:类名_方法名_参数列表
   * @param pjp
   * @param cacheKeyPrefix
   * @return
   */
  private String getCacheKey(ProceedingJoinPoint pjp,String cacheKeyPrefix){
    StringBuilder key = this.getCacheKeyPrefix(pjp,cacheKeyPrefix);

    Object[] args = pjp.getArgs();
    if(ArrayUtils.isNotEmpty(args)){
      List<Object> objs = new ArrayList<>();
      for (Object arg : args) {
        objs.add(arg);
      }
      String argsStr = StringUtils.join(objs,STR_SPILT);
      key.append(STR_SPILT).append(argsStr);
    }

    log.info("cacheKey:{}",key);

    return key.toString();

  }


  private StringBuilder getCacheKeyPrefix(ProceedingJoinPoint pjp,String cacheKeyPrefix){
    StringBuilder key = new StringBuilder();
    if(StringUtils.isNotBlank(cacheKeyPrefix)){
      key.append(cacheKeyPrefix);
    }else{
      String className = pjp.getTarget().getClass().getName();
      String methodName = pjp.getSignature().getName();
      key.append(className).append(STR_SPILT).append(methodName);
    }
    return key;
  }


  private Object saveOrUpdate(ProceedingJoinPoint pjp,String cacheKey,long expireTime){
    Object result = null;
    try {
      result = pjp.proceed();
      redisUtil.set(cacheKey,result,expireTime);
    } catch (Throwable throwable) {
      log.error("saveOrUpdate error:"+throwable.getMessage(),throwable);
    }

    return result;

  }


  private Object delete(ProceedingJoinPoint pjp,RedisCache cache){
    boolean isDelAll = cache.allEntries();
    String key;
    if(isDelAll){
      key = this.getCacheKeyPrefix(pjp,cache.cacheKeyPrefix()).toString();
      String pattern = key + "*";
      redisUtil.delFuzzyKeys(pattern);
      log.info("del fuzzy key->{} from cache",key);
    }else{
      key = this.getCacheKey(pjp,cache.cacheKeyPrefix());
      if(redisUtil.hasKey(key)){
        redisUtil.del(key);
        log.info("del key->{} from cache",key);
      }

    }

    Object result = null;
    try {
      result = pjp.proceed();
    } catch (Throwable throwable) {
      log.error("delete error:"+throwable.getMessage(),throwable);
    }

    return result;
  }


  private Object query(ProceedingJoinPoint pjp,String cacheKey,long expireTime){
    Object cacheValue = redisUtil.get(cacheKey);
    Object result = null;
    if(ObjectUtils.isNotEmpty(cacheValue)){
      result = cacheValue;
      log.info("get value from cache");
    }else{
      try {
        result = pjp.proceed();
        redisUtil.set(cacheKey,result,expireTime);
      } catch (Throwable throwable) {
        log.error("query error:"+throwable.getMessage(),throwable);
      }

    }

    return result;
  }





}
