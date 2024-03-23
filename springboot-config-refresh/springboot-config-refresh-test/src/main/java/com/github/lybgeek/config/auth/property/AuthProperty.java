package com.github.lybgeek.config.auth.property;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = AuthProperty.PREFIX)
public class AuthProperty {

    public static final String PREFIX = "lybgeek.auth";

    private boolean enabled;

    private String tokenKey = "token";

    private List<String> whitelistUrls;
}
