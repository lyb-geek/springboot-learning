package com.github.lybgeek.togglz.env.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.github.lybgeek.togglz.env.constant.TogglzConstant.*;

@ConfigurationProperties(prefix = EnvTogglzProperties.PREFIX)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvTogglzProperties {
    public static final String PREFIX = "lybgeek.togglz";

    private String stateRepositoryType = STORE_TYPE_INMEMORY;

    private String env = ENV_DEV;

    private String proxyType = PROXY_TYPE_BYTEBUDDY;

    private String fileStoreLocation;

    private String togglzTableName = DEFAULT_TOGGLZ_TABLE_NAME;

}
