package com.github.lybgeek.autoconfigure.dbtemplate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "db")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DbProperties {

    /**
     * if you want to use a dbTempate to operate datasouce,dbTemplateEnabled must be true,default false
     */
    private boolean dbTemplateEnabled = false;

    /**
     * jdbc driverClassName must can not be null
     */
    private String driverClassName;

    /**
     * datasource password must can not be null
     */
    private String password;

    /**
     * datasource username must can not be null
     */
    private String username;

    /**
     * datasource url must can not be null
     */
    private String url;
}
