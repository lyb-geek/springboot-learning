package com.github.lybgeek.el.plugin.support;


import com.github.lybgeek.el.plugin.ExpressionPlugin;
import com.github.lybgeek.el.util.hutool.HutoolExpressionUtil;

import java.util.Map;

public class HutoolExpressionPlugin implements ExpressionPlugin {
    @Override
    public <T> T evaluateExpression(Map<String, Object> rootObject, String expression, Class<T> returnType) {
        return HutoolExpressionUtil.evaluateExpression(rootObject,expression,returnType);
    }
}
