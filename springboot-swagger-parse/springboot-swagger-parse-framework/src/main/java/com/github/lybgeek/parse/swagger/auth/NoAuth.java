package com.github.lybgeek.parse.swagger.auth;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.github.lybgeek.parse.swagger.api.http.config.HTTPAuthConfig;
import lombok.Data;

/**
 * @description:
 *
 **/
@Data
@JsonTypeName("NONE")
public class NoAuth extends HTTPAuthConfig {
}
