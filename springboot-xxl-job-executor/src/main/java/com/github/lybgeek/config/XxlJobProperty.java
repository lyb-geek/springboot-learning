package com.github.lybgeek.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: xxl-job配置
 *
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperty {

    private String adminAddresses;

    private String accessToken;

    private String executorAppname;

    private String executorAddress;

    private String executorIp;

    private int executorPort;

    private String executorLogPath;

    private int executorLogRetentionDays;

}
