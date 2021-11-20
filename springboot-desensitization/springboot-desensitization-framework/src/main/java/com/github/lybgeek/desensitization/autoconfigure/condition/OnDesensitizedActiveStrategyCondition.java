package com.github.lybgeek.desensitization.autoconfigure.condition;


import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import static com.github.lybgeek.desensitization.property.DesensitizedProperties.PREFIX;
import static com.github.lybgeek.desensitization.property.DesensitizedProperties.STRATEGY;

public class OnDesensitizedActiveStrategyCondition implements Condition {


    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String strategy = context.getEnvironment().getProperty(PREFIX + "." + STRATEGY);
        if(metadata.isAnnotated(ConditionalOnDesensitizedByMybatis.class.getName())){
            return "mybatis".equalsIgnoreCase(strategy);
        }
        return false;
    }


}
