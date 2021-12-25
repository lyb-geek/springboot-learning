package com.github.lybgeek.circuitbreaker.framework.properties;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.github.lybgeek.circuitbreaker.framework.properties.CircuitBreakerMappingProperties.PREFIX;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = PREFIX)
public class CircuitBreakerMappingProperties {

    public static final String PREFIX = "circuit.breaker.mapping";

    /**
     * enable circuit breaker mapping default true,If the property is enabled, sentinel filter will be invalid
     */
    private boolean enabled = true;
}
