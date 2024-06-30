package com.github.lybgeek.el.util.hutool;

import cn.hutool.extra.expression.ExpressionUtil;

import java.util.HashMap;
import java.util.Map;

public class HutoolExpressionUtil {


    private HutoolExpressionUtil(){}


    /**
     * 执行表达式并返回结果。
     *
     * @param expression 表达式字符串
     * @param variables  变量映射，键为变量名，值为变量值
     * @return 表达式计算后的结果
     */
    public static <T> T evaluateExpression(Map<String, Object> variables,String expression, Class<T> returnType) {
        try {
            Object value = ExpressionUtil.eval(expression, variables);
            if(value != null && value.getClass().isAssignableFrom(returnType)){
                return (T)value;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error executing  expression: " + expression, e);
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