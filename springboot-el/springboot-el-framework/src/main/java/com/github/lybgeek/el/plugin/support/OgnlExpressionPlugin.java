package com.github.lybgeek.el.plugin.support;


import com.github.lybgeek.el.plugin.ExpressionPlugin;
import com.github.lybgeek.el.util.ognl.OgnlExpressionUtil;

import java.util.Map;

public class OgnlExpressionPlugin implements ExpressionPlugin {
    @Override
    public <T> T evaluateExpression(Map<String, Object> rootObject, String expression, Class<T> returnType) {
        expression = expression.replace("#","");
        return OgnlExpressionUtil.evaluateExpression(rootObject,expression,returnType);
    }
}
