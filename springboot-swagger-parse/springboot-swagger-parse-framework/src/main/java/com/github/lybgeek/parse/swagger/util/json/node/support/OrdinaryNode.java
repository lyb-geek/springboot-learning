package com.github.lybgeek.parse.swagger.util.json.node.support;

import com.github.lybgeek.parse.swagger.util.json.node.Node;

import java.util.List;

public class OrdinaryNode extends BaseNode {

    private Node proxyNode;

    public OrdinaryNode(String expression) throws Exception {
        super(expression);
    }

    protected OrdinaryNode(List<String> expressionFragments) throws Exception {
        super(expressionFragments);
    }

    @Override
    protected void init(String expression, List<String> expressionFragments)
            throws Exception {
//        if (expressionFragments.size() == 0) {
//            return;
//        }
//        Node[] nodes = new Node[]{
//                new OptionalNode(expressionFragments, false),
//                new SingleNode(expressionFragments, false),
//                new RepeatNode(expressionFragments, false),
//                new LinkNode(expressionFragments, false)
//        };
//        for (Node node : nodes) {
//            if (node.test()) {
//                proxyNode = node;
//                proxyNode.init();
//                break;
//            }
//        }
    }

    @Override
    protected String random(String expression, List<String> expressionFragments)
            throws Exception {
        if (proxyNode == null) {
            return "";
        }
        return proxyNode.random();
    }

}
