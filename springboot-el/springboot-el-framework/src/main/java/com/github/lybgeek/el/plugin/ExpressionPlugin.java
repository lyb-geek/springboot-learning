package com.github.lybgeek.el.plugin;


import org.springframework.core.Ordered;

import java.util.Map;

@FunctionalInterface
public interface ExpressionPlugin extends Ordered {

    <T> T evaluateExpression(Map<String, Object> rootObject, String expression, Class<T> returnType);

    @Override
    default int getOrder() {
        return 0;
    }
}
