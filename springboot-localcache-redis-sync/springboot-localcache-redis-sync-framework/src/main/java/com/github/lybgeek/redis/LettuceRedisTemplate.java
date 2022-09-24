package com.github.lybgeek.redis;

import com.alibaba.fastjson.JSON;
import io.lettuce.core.RedisClient;
import io.lettuce.core.TrackingArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.support.caching.CacheAccessor;
import io.lettuce.core.support.caching.CacheFrontend;
import io.lettuce.core.support.caching.ClientSideCaching;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class LettuceRedisTemplate{

    private RedisClient redisClient;

    private Map<String,String> clientCache = new ConcurrentHashMap<>();


    public LettuceRedisTemplate(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    /**
     * 匹配key 返回匹配的key列表
     * @param keys
     * @return
     */
    public List<String> keys(String keys){
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        RedisCommands<String, String> commands = connect.sync();
        List<String> keyList = commands.keys(keys);
        connect.close();
        redisClient.shutdown();
        return keyList;
    }
    /**
     * 设置key,和value 失效时间为一天
     * @param key
     * @param value
     */
    public void set(String key,String value){
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        RedisCommands<String, String> commands = connect.sync();
        commands.setex(key, TimeUnit.DAYS.toSeconds(1),value);
        connect.close();
    }
    /**
     * 设置key,和value 失效时间为一天
     * @param key
     * @param value
     */
    public  void set(String key, Object value){
        String valueStr = null;
        if(value != null){
            valueStr = JSON.toJSONString(value);
        }
        set(key, valueStr);
    }

    public String get(String key){
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        RedisCommands<String, String> commands = connect.sync();
        String s = commands.get(key);
        connect.close();
        return s;
    }

    /**
     * 客户端缓存同步
     *
     */
    public String getClientCacheValue(String key){
       return this.getClientCacheValue(clientCache,key);
    }

    /**
     * 客户端缓存同步
     *
     */
    public String getClientCacheValue(Map<String,String> clientCache,String key){
        StatefulRedisConnection<String, String> connect = redisClient.connect();
      //  Map<String,String> clientCache = new ConcurrentHashMap<>();
        CacheFrontend<String,String> frontend = ClientSideCaching.enable(CacheAccessor.forMap(clientCache),
                connect, TrackingArgs.Builder.enabled().noloop());
        return frontend.get(key);

    }


    @PreDestroy
    public void destory(){
        redisClient.shutdown();
    }

}
