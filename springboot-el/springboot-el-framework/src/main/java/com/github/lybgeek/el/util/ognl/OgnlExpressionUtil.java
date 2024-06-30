package com.github.lybgeek.el.util.ognl;

import com.github.lybgeek.el.util.spring.SpringExpressionUtil;

import java.util.HashMap;
import java.util.Map;

public class OgnlExpressionUtil {


    private OgnlExpressionUtil(){}

    /**
     * Evaluates the given Ognl EL expression against the provided root object.
     * 
     * @param rootObject The object to use as the root of the expression evaluation.
     * @param expressionString The OGNL EL expression to evaluate.
     * @param returnType The expected return type.
     * @return The result of the expression evaluation.
     */
    public static <T> T evaluateExpression(Map<String, Object> rootObject, String expressionString, Class<T> returnType) {
        Object value = OgnlCache.getValue(expressionString, rootObject);
        if(value != null && value.getClass().isAssignableFrom(returnType)){
            return (T)value;
        }

        return null;
    }

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("name","lybgeek");
        map.put("hello","world");
        System.out.println(OgnlExpressionUtil.evaluateExpression(map,"#root.name",String.class));

        System.out.println(SpringExpressionUtil.evaluateExpression(map,"#root.get('hello')",String.class));
    }
}