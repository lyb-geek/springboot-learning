package com.github.lybgeek.config.property;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = RefreshConfigProperty.PREFIX)
public class RefreshConfigProperty {
    public static final String PREFIX = "lybgeek.refresh";

    private boolean enabled = true;

    private boolean isRefreshAsync = true;

    private List<String> remoteEnvChangeUrls;

    private String storePropertiesLocation;
}
