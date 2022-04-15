package com.github.lybgeek.git.endpoint;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.info.GitProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.HashMap;

@Endpoint(id = "git")
@Component
public class GitEndpoint {


    @Autowired(required = false)
    private GitProperties gitProperties;

    @ReadOperation
    public Object info() throws IOException {

        if(ObjectUtils.isEmpty(gitProperties)){
            return new HashMap<>();
        }


        return gitProperties;
    }
}
