package com.github.lybgeek.parse.swagger.auth;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @description:
 **/
@Data
public class BasicAuth extends HTTPAuth {
    private String userName;
    private String password;

    @Override
    public boolean isValid() {
        return StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password);
    }
}
