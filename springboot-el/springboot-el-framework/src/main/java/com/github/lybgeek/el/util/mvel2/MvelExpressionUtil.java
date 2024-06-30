package com.github.lybgeek.el.util.mvel2;

import org.mvel2.MVEL;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.MapVariableResolverFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * https://blog.csdn.net/qq877728715/article/details/128786841
 */
public final class MvelExpressionUtil {

    private MvelExpressionUtil() {
    }

    /**
     * 执行MVEL表达式并返回结果。
     *
     * @param expression MVEL表达式字符串
     * @param variables  变量映射，键为变量名，值为变量值
     * @return 表达式计算后的结果
     */
    public static <T> T evaluateExpression(Map<String, Object> variables,String expression, Class<T> returnType) {
        try {
            VariableResolverFactory factory = new MapVariableResolverFactory(variables != null ? variables : new HashMap<>());
            Object value = MVEL.eval(expression, factory);
            if(value != null && value.getClass().isAssignableFrom(returnType)){
                return (T)value;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error executing MVEL expression: " + expression, e);
        }

        return null;
    }

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("name","lybgeek");
        map.put("hello","world");

        Map<String,Object> variables = new HashMap<>();
        variables.put("root",map);
        System.out.println(evaluateExpression(variables,"root.name",String.class));
    }


}
