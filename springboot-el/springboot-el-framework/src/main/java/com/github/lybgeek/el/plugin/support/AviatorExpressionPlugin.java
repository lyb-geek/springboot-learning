package com.github.lybgeek.el.plugin.support;


import com.github.lybgeek.el.plugin.ExpressionPlugin;
import com.github.lybgeek.el.util.aviator.AviatorExpressionUtil;

import java.util.Map;

public class AviatorExpressionPlugin implements ExpressionPlugin {
    @Override
    public <T> T evaluateExpression(Map<String, Object> rootObject, String expression, Class<T> returnType) {
        return AviatorExpressionUtil.evaluateExpression(rootObject, expression, returnType);
    }
}
