package com.github.lybgeek.el.plugin.support;


import com.github.lybgeek.el.plugin.ExpressionPlugin;
import com.github.lybgeek.el.util.spring.SpringExpressionUtil;

import java.util.Map;

public class SpringExpressionPlugin implements ExpressionPlugin {
    @Override
    public <T> T evaluateExpression(Map<String, Object> rootObject, String expression, Class<T> returnType) {
        return SpringExpressionUtil.evaluateExpression(rootObject,expression,returnType);
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
