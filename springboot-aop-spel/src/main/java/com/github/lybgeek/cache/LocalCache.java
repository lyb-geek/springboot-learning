package com.github.lybgeek.cache;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum LocalCache {

    INSTANCE;

    private  Map<String,Object> localCacheMap = new ConcurrentHashMap<>();

    public void put(String key,Object obj){
        localCacheMap.put(key,obj);
    }

    public Object get(String key){
        return localCacheMap.get(key);
    }

    public void printCacheInfo(){
        System.out.println(String.format("cacheInfo:%s",localCacheMap));

    }

}
