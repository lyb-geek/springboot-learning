package com.github.lybgeek.aop.ast;


import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Names;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

public class CostTimeRecordTreeTranslator extends TreeTranslator {

    /**
     * 日志输出工具类
     */
    private Messager meessager;

    /**
     * 抽象语法树
     */
    private JavacTrees trees;

    /**
     * 封装了创建或者修改AST节点的一些方法
     */
    private TreeMaker treeMaker;

    /**
     * 封装了操作标识符的方法
     */
    private Names names;

    private JCTree.JCMethodDecl methodDecl;

    public CostTimeRecordTreeTranslator(Messager meessager, JavacTrees trees, TreeMaker treeMaker, Names names, JCTree.JCMethodDecl jcMethodDecl) {
        this.meessager = meessager;
        this.trees = trees;
        this.treeMaker = treeMaker;
        this.names = names;
        this.methodDecl = jcMethodDecl;
    }

    @Override
    public void visitMethodDef(JCTree.JCMethodDecl jcMethodDecl) {
        if(methodDecl == jcMethodDecl){
            List<JCTree.JCStatement> statements = jcMethodDecl.body.getStatements();
            // long startTime = System.currentTimeMillis()
            JCTree.JCVariableDecl startTime = treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER),
                    names.fromString("startTime"),
                    memberAccess("java.lang.Long"),
                    treeMaker.Apply(List.nil(),
                            memberAccess("java.lang.System.currentTimeMillis"),
                            List.nil()
                    )
            );

            // long endTime = System.currentTimeMillis()
            JCTree.JCVariableDecl endTime = treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER),
                    names.fromString("endTime"),
                    memberAccess("java.lang.Long"),
                    treeMaker.Apply(List.nil(),
                            memberAccess("java.lang.System.currentTimeMillis"),
                            List.nil()
                    )
            );

            // long costTime = endTime - startTime
            JCTree.JCVariableDecl costTime = treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER),
                    names.fromString("costTime"),
                    memberAccess("java.lang.Long"),
                    treeMaker.Binary(JCTree.Tag.MINUS,
                            treeMaker.Ident(names.fromString("endTime")),
                            treeMaker.Ident(names.fromString("startTime"))
                            )
            );

            // String msg = "costTime = "+costTime+ "(ms)"
            JCTree.JCVariableDecl msg = treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER),
                    names.fromString("msg"),
                    memberAccess("java.lang.String"),
                    treeMaker.Apply(
                            List.of(memberAccess("java.lang.String"),memberAccess("java.lang.Object")),
                            memberAccess("java.lang.String.format"),
                            List.of(treeMaker.Literal("costTime = %s(ms)"),treeMaker.Ident(names.fromString("costTime")))
                    )
            );

            // System.out.println(msg)
            JCTree.JCExpressionStatement printExpressionStatement = treeMaker.Exec(treeMaker.Apply(List.of(memberAccess("java.lang.String")),
                    memberAccess("java.lang.System.out.println"),
                    List.of(treeMaker.Ident(names.fromString("msg")))
            ));

            if(jcMethodDecl.getReturnType().type.getTag() == TypeTag.VOID){
                statements = statements.prepend(startTime)
                        .append(endTime)
                        .append(costTime)
                        .append(msg)
                        .append(printExpressionStatement);
            }else{
                List<JCTree.JCStatement> newExpressionStatement = List.of(startTime);
                List<JCTree.JCStatement> excludeReturnStatements = statements.take(statements.size() - 1);
                JCTree.JCStatement returnStatement = statements.last();
                statements = newExpressionStatement
                        .appendList(excludeReturnStatements)
                        .append(endTime)
                        .append(costTime)
                        .append(msg)
                        .append(printExpressionStatement)
                        .append(returnStatement);

            }


            JCTree.JCBlock jcBlock = treeMaker.Block(0,statements);
            jcMethodDecl = treeMaker.MethodDef(jcMethodDecl.sym,jcBlock);

            meessager.printMessage(Diagnostic.Kind.NOTE,"method:" + jcMethodDecl);
        }

        super.visitMethodDef(jcMethodDecl);
    }


    /**
     * 创建 域/方法 的多级访问, 方法的标识只能是最后一个
     * @param components 比如java.lang.System.out.println
     * @return
     */
    public JCTree.JCExpression memberAccess(String components) {
        String[] componentArray = components.split("\\.");
        JCTree.JCExpression expr = treeMaker.Ident(names.fromString(componentArray[0]));
        for (int i = 1; i < componentArray.length; i++) {
            expr = treeMaker.Select(expr, names.fromString(componentArray[i]));
        }
        return expr;
    }
}
