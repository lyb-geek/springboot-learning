package com.github.lybgeek.sms.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsConfig {

    /**
     * sms accessKeyId
     */
    private String accessKeyId;
    /**
     * sms accessKeySecret
     */
    private String accessKeySecret;
    /**
     * sms signName
     */
    private String signName;
    /**
     * sms templateCode
     */
    private String templateCode;
    /**
     * sms endpoint
     */
    private String endpoint;
    /**
     * sms regionId
     */
    private String regionId;


}
