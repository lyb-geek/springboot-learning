package com.github.lybgeek.advise.properties;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = PointcutProperites.PREFIX)
public class PointcutProperites {

    public final static String PREFIX = "lybgeek.pointcut";

    private String expression;
}
