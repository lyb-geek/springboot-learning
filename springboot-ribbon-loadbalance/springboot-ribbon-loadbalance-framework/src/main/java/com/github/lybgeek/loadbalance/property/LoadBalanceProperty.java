package com.github.lybgeek.loadbalance.property;


import com.github.lybgeek.loadbalance.model.RuleDefinition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.github.lybgeek.loadbalance.constant.LoadBalanceConstant.RULE_JOIN;
import static com.github.lybgeek.loadbalance.property.LoadBalanceProperty.PREFIX;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = PREFIX)
public class LoadBalanceProperty {

    public static final String PREFIX = "lybgeek.loadbalance";

    private List<RuleDefinition> rules;

    public Map<String,RuleDefinition> getRuleMap(){
        if(CollectionUtils.isEmpty(rules)){
            return Collections.emptyMap();
        }

        Map<String,RuleDefinition> ruleDefinitionMap = new LinkedHashMap<>();
        for (RuleDefinition rule : rules) {
            String key = rule.getServiceName() + RULE_JOIN + rule.getNamespace();
            ruleDefinitionMap.put(key,rule);
        }

        return Collections.unmodifiableMap(ruleDefinitionMap);
    }
}
