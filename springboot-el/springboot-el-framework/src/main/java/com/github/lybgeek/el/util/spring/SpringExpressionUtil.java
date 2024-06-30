package com.github.lybgeek.el.util.spring;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.HashMap;
import java.util.Map;

public class SpringExpressionUtil {

    private static final SpelExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    private SpringExpressionUtil(){}

    /**
     * Evaluates the given Spring EL expression against the provided root object.
     * 
     * @param rootObject The object to use as the root of the expression evaluation.
     * @param expressionString The Spring EL expression to evaluate.
     * @param returnType The expected return type.
     * @return The result of the expression evaluation.
     */
    public static <T> T evaluateExpression(Map<String, Object> rootObject, String expressionString, Class<T> returnType) {
        StandardEvaluationContext context = new StandardEvaluationContext(rootObject);
        rootObject.forEach(context::setVariable);
        return EXPRESSION_PARSER.parseExpression(expressionString).getValue(context,returnType);
    }



    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("name","lybgeek");
        map.put("hello","world");
        System.out.println(evaluateExpression(map,"#root.get('name')",String.class));

    }
}