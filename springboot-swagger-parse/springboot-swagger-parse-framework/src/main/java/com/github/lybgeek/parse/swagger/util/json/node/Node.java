package com.github.lybgeek.parse.swagger.util.json.node;

public interface Node {

    String getExpression();

    String random() throws Exception;

    boolean test();

    void init() throws Exception;

    boolean isInitialized();
}
