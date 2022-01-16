package com.github.lybgeek.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.github.lybgeek.config.LicenseProperties.PREFIX;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = PREFIX)
public class LicenseProperties {

    public static final String PREFIX = "lybgeek.license";

    private String code;


}
