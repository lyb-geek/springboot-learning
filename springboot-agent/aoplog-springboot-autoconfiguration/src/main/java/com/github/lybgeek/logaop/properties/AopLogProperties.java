package com.github.lybgeek.logaop.properties;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "servicelog")
public class AopLogProperties {

    /**
     * serviceLog enabled;default true
     */
    private boolean enabled = true;

    /**
     * pointcut value
     */
    private String pointcut;
}
