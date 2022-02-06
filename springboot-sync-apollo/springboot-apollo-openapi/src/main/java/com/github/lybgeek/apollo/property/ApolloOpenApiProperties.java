package com.github.lybgeek.apollo.property;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = ApolloOpenApiProperties.PREFIX)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApolloOpenApiProperties {
    public final static String PREFIX = "apollo.openapi";

    private String portalUrl;

    private String token;


}
