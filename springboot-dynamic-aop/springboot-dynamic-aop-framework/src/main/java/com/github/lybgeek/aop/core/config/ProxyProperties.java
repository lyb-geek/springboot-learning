package com.github.lybgeek.aop.core.config;


import com.github.lybgeek.aop.core.model.ProxyMetaDefinition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.github.lybgeek.aop.core.config.ProxyProperties.PREFIX;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = PREFIX)
@Slf4j
public class ProxyProperties {

    public final static String PREFIX = "lybgeek.proxy";

    private boolean enabled = true;

    private List<ProxyMetaDefinition> metaDefinitions = new ArrayList<>();

    private boolean globalAdvisorEnabled = true;

    private String globalBeProxiedBasePackage;

    private String globalPointcut;

    public String getGlobalPointcut() {
        if(globalAdvisorEnabled){
            if(StringUtils.isEmpty(globalPointcut)){
                if(StringUtils.hasText(globalBeProxiedBasePackage)){
                    StringBuilder sb = new StringBuilder();
                    globalPointcut = sb.append("within(")
                            .append(globalBeProxiedBasePackage)
                            .append("..*)").toString();
                 log.info(">>>>>>>>>>>>>>>>>>>>>>>>>> The classes and subclasses under the package : 【{}】 are about to be proxied with pointcut expression:【{}】",globalBeProxiedBasePackage,globalPointcut);
                }
            }
            Assert.notNull(globalPointcut,"GlobalPointcut can not be null");
        }
        return globalPointcut;
    }
}
