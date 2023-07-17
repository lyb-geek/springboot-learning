package com.github.lybgeek.apolloconfig.property;


import com.ctrip.framework.apollo.core.ConfigConsts;
import com.github.lybgeek.apolloconfig.constant.ApolloConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.github.lybgeek.apolloconfig.property.ApolloOpenApiProperty.PREFIX;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = PREFIX)
public class ApolloOpenApiProperty {
    public static final String PREFIX = "apollo.openapi";

    private String appId;

    private String env;

    private String clusterName = ConfigConsts.CLUSTER_NAME_DEFAULT;

    private String namespaceName = ConfigConsts.NAMESPACE_APPLICATION;

    private String token;

    private String portalUrl = ApolloConstant.DEFAULT_PORTAL_URL;

    private String dataChangeCreatedBy = ApolloConstant.DEFAULT_OPERATOR;

    private String releasedBy = ApolloConstant.DEFAULT_OPERATOR;

    private boolean isCallBackAsync = false;


}
