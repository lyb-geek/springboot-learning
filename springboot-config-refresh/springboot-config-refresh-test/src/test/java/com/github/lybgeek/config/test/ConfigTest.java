package com.github.lybgeek.config.test;


import cn.hutool.core.collection.CollectionUtil;
import com.dtflys.forest.Forest;
import com.dtflys.forest.http.ForestResponse;
import com.github.lybgeek.config.auth.property.AuthProperty;
import com.github.lybgeek.config.helper.PrintUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.lybgeek.config.auth.filter.AuthHandlerInterceptor.MOCK_TOKEN_VALUE;
import static com.github.lybgeek.config.controller.EnvController.HELLO_KEY;

public class ConfigTest {

    public static final String TOKEN_KEY = "token";

    private final List<String> serverUrls = CollectionUtil.newArrayList("http://localhost:8081","http://localhost:8082");
    
    @Test
    public void testGetProperty(){
        for (String serverUrl : serverUrls) {
            callRemote(serverUrl + "/config/get");
        }

    }

    @Test
    public void testGetEnvProperties(){
        for (String serverUrl : serverUrls) {
            callRemote(serverUrl + "/env/properties");
        }
    }

    @Test
    public void testGetEnvHello(){
        for (String serverUrl : serverUrls) {
            callRemote(serverUrl + "/env/hello");
        }
    }

    @Test
    public void testChangeEnvHello(){
        refreshProperty(HELLO_KEY, "hello-world");
    }
    
    @Test
    public void testChangeLoggerLevel(){
        String level = "debug";
        ForestResponse response = Forest.get("http://localhost:8081/logger/change/" + level ).executeAsResponse();
        PrintUtils.print(response.getContent());
    }
    
    @Test
    public void testRefreshPropertyEnabled(){
        String name = AuthProperty.PREFIX + ".enabled";
        String value = "true";
        refreshProperty(name, value);
    }

    @Test
    public void testRefreshPropertyTokenKey(){
        String name = AuthProperty.PREFIX + ".tokenKey";
        refreshProperty(name, TOKEN_KEY);
    }



    @Test
    public void testRefreshPropertyWhitelistUrls(){
        String name = AuthProperty.PREFIX + ".whitelistUrls";
        List<String> whitelistUrls = new ArrayList<>();
        whitelistUrls.add("/config/refresh");
        whitelistUrls.add("/config/get");
        String value = String.join(",", whitelistUrls);
        refreshProperty(name, value);
    }

    private void refreshProperty(String name, String value) {
        Map<String,String> map = new HashMap<>();
        map.put("name", name);
        map.put("value", value);
        ForestResponse response = Forest.get("http://localhost:8081/config/refresh").addQuery(map).addHeader(TOKEN_KEY, MOCK_TOKEN_VALUE).executeAsResponse();
        PrintUtils.print(response.getContent());
    }

    private void callRemote(String url) {
        ForestResponse response = Forest.get(url).executeAsResponse();
        PrintUtils.print(response.getContent());
    }
}
