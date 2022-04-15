package com.github.lybgeek.git.endpoint;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

@Endpoint(id = "gitDetail")
@Slf4j
@Component
public class GitDetailEndPoint {

    @ReadOperation
    public Object detail() throws IOException {


        Properties props = null;
        try {
            props = PropertiesLoaderUtils.loadAllProperties("git.properties");
            return props;
        } catch (IOException e) {
            log.error("git.properties not found");
        } finally {
        }
        return new HashMap<>();
    }
}
