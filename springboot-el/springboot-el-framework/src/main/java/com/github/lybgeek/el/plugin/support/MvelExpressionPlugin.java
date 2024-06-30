package com.github.lybgeek.el.plugin.support;


import com.github.lybgeek.el.plugin.ExpressionPlugin;
import com.github.lybgeek.el.util.mvel2.MvelExpressionUtil;

import java.util.Map;

public class MvelExpressionPlugin implements ExpressionPlugin {
    @Override
    public <T> T evaluateExpression(Map<String, Object> rootObject, String expression, Class<T> returnType) {
        expression = expression.replace("#","");
        return MvelExpressionUtil.evaluateExpression(rootObject,expression,returnType);
    }
}
