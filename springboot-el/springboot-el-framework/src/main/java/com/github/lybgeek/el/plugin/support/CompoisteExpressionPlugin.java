package com.github.lybgeek.el.plugin.support;


import cn.hutool.core.collection.CollectionUtil;
import com.github.lybgeek.el.plugin.ExpressionPlugin;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.core.OrderComparator;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CompoisteExpressionPlugin implements ExpressionPlugin, SmartInitializingSingleton {

    private final ObjectProvider<List<ExpressionPlugin>> expressionPlugins;

    private List<ExpressionPlugin> expressionPluginList;
    @Override
    public <T> T evaluateExpression(Map<String, Object> rootObject, String expression, Class<T> returnType) {
        if(!CollectionUtils.isEmpty(expressionPluginList)){
            for (ExpressionPlugin expressionPlugin : expressionPluginList) {
                T result = expressionPlugin.evaluateExpression(rootObject,expression,returnType);
                if(result != null){
                    return result;
                }
            }
        }
        return null;
    }

    @Override
    public void afterSingletonsInstantiated() {
        expressionPluginList = expressionPlugins.getIfAvailable();
        if(!CollectionUtils.isEmpty(expressionPluginList)){
            OrderComparator.sort(expressionPluginList);
        }
    }
}
