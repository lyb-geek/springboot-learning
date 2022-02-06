package com.github.lybgeek.apollo.property;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = AppInfoProperties.PREFIX)
public class AppInfoProperties {

    public final static String PREFIX = "apollo.appinfo";

    private String env = "dev";

    private String appId;

    private String clusterName = "default";

    private String nameSpaceName = "application";

    /**
     * 授权用户
     */
    private String authUser = "apollo";


}
