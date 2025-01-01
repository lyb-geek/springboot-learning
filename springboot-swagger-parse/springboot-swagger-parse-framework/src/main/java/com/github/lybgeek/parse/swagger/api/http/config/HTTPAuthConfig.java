package com.github.lybgeek.parse.swagger.api.http.config;


import com.github.lybgeek.parse.swagger.auth.BasicAuth;
import com.github.lybgeek.parse.swagger.auth.DigestAuth;
import com.github.lybgeek.parse.swagger.auth.HTTPAuth;
import lombok.Data;

import java.util.HashMap;

/**
 * @description:http 认证配置
 *
 **/
@Data
public class HTTPAuthConfig {

    /**
     * 认证方式
     * {@link HTTPAuthType}
     */
    private String authType = HTTPAuthType.NONE.name();
    private BasicAuth basicAuth = new BasicAuth();
    private DigestAuth digestAuth = new DigestAuth();

    public boolean isHTTPAuthValid() {
        HashMap<String, HTTPAuth> httpAuthHashMap = new HashMap<>(2);
        httpAuthHashMap.put(HTTPAuthType.BASIC.name(), basicAuth);
        httpAuthHashMap.put(HTTPAuthType.DIGEST.name(), digestAuth);
        HTTPAuth httpAuth = httpAuthHashMap.get(authType);
        return httpAuth != null && httpAuth.isValid();
    }

    /**
     * http 认证方式
     */
    public enum HTTPAuthType {
        NONE,
        BASIC,
        DIGEST
    }
}
