package com.github.lybgeek.el.util.aviator;

import com.googlecode.aviator.AviatorEvaluator;

import java.util.HashMap;
import java.util.Map;

/**
 * @see <a href="https://developer.aliyun.com/article/608829">...</a>
 * Aviator是一个高性能、轻量级的java语言实现的表达式求值引擎，主要用于各种表达式的动态求值。
 */
public final class AviatorExpressionUtil {


    private AviatorExpressionUtil() {
    }

    /**
     * 执行Aviator表达式并返回结果
     *
     * @param expression Aviator表达式字符串
     * @param env        上下文环境，可以包含变量和函数
     * @return 表达式计算后的结果
     */
    public static <T> T evaluateExpression(Map<String, Object> env,String expression, Class<T> returnType) {
        Object value = AviatorEvaluator.execute(expression, env);
        if(value != null && value.getClass().isAssignableFrom(returnType)){
            return (T)value;
        }

        return null;

    }

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("name","lybgeek");
        map.put("hello","world");

        Map<String,Object> env = new HashMap<>();
        env.put("root",map);
        System.out.println(evaluateExpression(env,"#root.name",String.class));
    }



}