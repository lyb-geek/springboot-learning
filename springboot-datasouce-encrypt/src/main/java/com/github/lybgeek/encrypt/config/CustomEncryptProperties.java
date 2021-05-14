package com.github.lybgeek.encrypt.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "custom.encrypt")
public class CustomEncryptProperties {

    /**
     * 加密密钥
     */
    private String secretKey;

    /**
     * 是否开启加密，默认false
     */
    private boolean enabled;

}
