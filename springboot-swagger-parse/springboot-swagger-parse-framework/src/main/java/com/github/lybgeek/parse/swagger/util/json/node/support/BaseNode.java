package com.github.lybgeek.parse.swagger.util.json.node.support;

import com.github.lybgeek.parse.swagger.util.json.node.Node;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseNode implements Node {

    private String expression;
    private List<String> expressionFragments;
    private boolean initialized;

    protected BaseNode(String expression) throws Exception {
        this(expression, true);
    }

    protected BaseNode(String expression, boolean initialize) throws Exception {
        this.expression = expression;
        this.expressionFragments = spliceExpression(expression);
        if (initialize) {
            init();
        }
    }

    protected BaseNode(List<String> expressionFragments) throws Exception {
        this(expressionFragments, true);
    }

    protected BaseNode(List<String> expressionFragments, boolean initialize)
            throws Exception {
        this.expressionFragments = expressionFragments;
        StringBuilder stringBuilder = new StringBuilder();
        for (String fragment : expressionFragments) {
            stringBuilder.append(fragment);
        }
        this.expression = stringBuilder.toString();
        if (initialize) {
            init();
        }
    }

    @Override
    public String random() throws Exception {
        if (!initialized) {
            throw new Exception();
        }
        return random(expression, expressionFragments);
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public boolean test() {
        return test(expression, expressionFragments);
    }

    @Override
    public void init() throws Exception {
        if (!initialized) {
            if (!test()) {
                throw new Exception();
            }
            init(expression, expressionFragments);
            initialized = true;
        }
    }

    @Override
    public String getExpression() {
        return expression;
    }

    protected String random(String expression, List<String> expressionFragments)
            throws Exception {
        return null;
    }

    protected void init(String expression, List<String> expressionFragments)
            throws Exception {

    }

    protected boolean test(String expression, List<String> expressionFragments) {
        return true;
    }

    private List<String> spliceExpression(String expression) throws Exception {
        int l = 0;
        int r = expression.length();
        List<String> fragments = new ArrayList<>();
        while (true) {
            String result = findFirst(expression, l, r);
            if (result == null || result.isEmpty()) {
                break;
            }
            fragments.add(result);
            l += result.length();
        }
        return fragments;
    }

    private String findFirst(String expression, int l, int r) throws Exception {
        if (l == r) {
            return null;
        }
        if (expression.charAt(l) == '\\') {
            if (l + 1 >= r) {
                throw new Exception(expression);
            }
            return expression.substring(l, l + 2);
        }
        if (expression.charAt(l) == '[') {
            int i = l + 1;
            while (i < r) {
                if (expression.charAt(i) == ']') {
                    return expression.substring(l, i + 1);
                }
                if (expression.charAt(i) == '\\') {
                    i++;
                }
                i++;
            }
            throw new Exception(expression);
        }
        if (expression.charAt(l) == '{') {
            int i = l + 1;
            boolean hasDelimiter = false;
            while (i < r) {
                if (expression.charAt(i) == '}') {
                    return expression.substring(l, i + 1);
                }
                if (expression.charAt(i) == ',') {
                    if (hasDelimiter) {
                        throw new Exception(expression);
                    }
                    hasDelimiter = true;
                    i++;
                    continue;
                }
                if (expression.charAt(i) < '0' || expression.charAt(i) > '9') {
                    throw new Exception(expression);
                }
                i++;
            }
            throw new Exception(expression);
        }
        if (expression.charAt(l) == '(') {
            int i = l + 1;
            while (true) {
                String result = findFirst(expression, i, r);
                if (result == null || result.length() == 0 || result.length() + i >= r) {
                    throw new Exception(expression);
                }
                i += result.length();
                if (expression.charAt(i) == ')') {
                    return expression.substring(l, i + 1);
                }
            }
        }
        return expression.substring(l, l + 1);
    }

}
