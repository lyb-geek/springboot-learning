package com.github.lybgeek.json.util;


import com.alibaba.fastjson.JSON;
import com.github.lybgeek.json.ognl.OgnlCache;

import java.util.Map;

public final class JsonUtil {

    private JsonUtil(){}

    public static <T> T parse(String jsonStr, Class<T> clazz) throws Exception {
        return JSON.parseObject(jsonStr, clazz);
    }

    public static Object getValue(Map map, String path) throws Exception {
         return OgnlCache.getValue(path,map);
    }
}
