package com.github.lybgeek.parse.swagger.auth;

import lombok.Data;

/**
 * @description:http 认证配置
 *
 **/
@Data
public abstract class HTTPAuth {
    public abstract boolean isValid();
}
