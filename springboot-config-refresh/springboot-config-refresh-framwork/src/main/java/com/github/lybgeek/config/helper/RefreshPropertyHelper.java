package com.github.lybgeek.config.helper;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.URLUtil;
import com.dtflys.forest.Forest;
import com.dtflys.forest.http.ForestResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class RefreshPropertyHelper implements ApplicationListener<WebServerInitializedEvent> {

    // JUST FOR TESTING
    private  String TESTING_ENV_CHANGE_URL;

    private final InetUtils inetUtils;

    private final WebEndpointProperties webEndpointProperties;


    public void refresh(String key,Object value,String...remoteEnvChangeUrl){
        if(StringUtils.isEmpty(key) || StringUtils.isEmpty(value)){
            return;
        }
        Map<String,Object> params = new HashMap<>();
        params.put("name",key);
        params.put("value",value);
        getEnvUrl(remoteEnvChangeUrl).forEach(envUrl -> {
            ForestResponse response = Forest.post(envUrl).contentTypeJson().addBody(params).executeAsResponse();
            if(response.isSuccess()){
                log.info("Refresh property success, syncUrl:{}, content: {}", envUrl,response.getContent());
            }else{
                log.error("Refresh property fail, syncUrl:{}, content: {}", envUrl,response.getContent());
            }

        });

    }


    private Set<String> getEnvUrl(String...remoteEnvChangeUrl){
        if(ArrayUtil.isEmpty(remoteEnvChangeUrl)){
            return CollectionUtil.newHashSet(TESTING_ENV_CHANGE_URL);
        }
        return CollectionUtil.newHashSet(remoteEnvChangeUrl);
    }


    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        String host = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
        String port = String.valueOf(event.getWebServer().getPort());
        String url = host + ":" + port + webEndpointProperties.getBasePath() + "/env";
        TESTING_ENV_CHANGE_URL = URLUtil.normalize(url);
    }
}
