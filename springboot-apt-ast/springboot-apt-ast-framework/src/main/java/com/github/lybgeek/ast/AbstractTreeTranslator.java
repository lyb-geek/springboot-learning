package com.github.lybgeek.ast;


import com.sun.source.tree.Tree;
import com.sun.source.util.TreePath;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;

import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.ArrayList;

/**
 * 参考：{@link https://blog.csdn.net/a_zhenzhen/article/details/86065063}
 */
public abstract class AbstractTreeTranslator extends TreeTranslator {

    /**
     * 封装了创建或者修改AST节点的一些方法
     */
    protected TreeMaker treeMaker;

    /**
     * 封装了操作标识符的方法
     */
    protected Names names;

    /**
     * 日志输出工具类
     */
    protected Messager meessager;

    /**
     * 抽象语法树
     */
    private JavacTrees trees;

    public AbstractTreeTranslator(TreeMaker treeMaker, Names names, Messager meessager) {
        this.treeMaker = treeMaker;
        this.names = names;
        this.meessager = meessager;
    }


    /**
     * 根据字符串获取Name
     * @param s
     * @return
     */
    public Name getNameFromString(String s) { return names.fromString(s); }


    /**
     * 创建变量语句
     * @param modifiers 访问修饰符
     * @param name 参数名称
     * @param varType 参数类型
     * @param init 初始化赋值语句
     * 示例
     *   JCTree.JCVariableDecl var = makeVarDef(treeMaker.Modifiers(0), "xiao", memberAccess("java.lang.String"), treeMaker.Literal("methodName"));
     *   生成语句为：String xiao = "methodName";
     * @return
     */
    public JCTree.JCVariableDecl makeVarDef(JCTree.JCModifiers modifiers, JCTree.JCExpression varType,String name, JCTree.JCExpression init) {
        return treeMaker.VarDef(
                modifiers,
                getNameFromString(name),
                varType,
                init
        );
    }

    /**
     * 创建 域/方法 的多级访问, 方法的标识只能是最后一个
     * @param components 比如java.lang.System.out.println
     * @return
     */
    public JCTree.JCExpression memberAccess(String components) {
        String[] componentArray = components.split("\\.");
        JCTree.JCExpression expr = treeMaker.Ident(getNameFromString(componentArray[0]));
        for (int i = 1; i < componentArray.length; i++) {
            expr = treeMaker.Select(expr, getNameFromString(componentArray[i]));
        }
        return expr;
    }

    /**
     * 给变量赋值
     * @param lhs
     * @param rhs
     * @return
     * 示例：makeAssignment(treeMaker.Ident(getNameFromString("xiao")), treeMaker.Literal("assignment test"));
     * 生成的赋值语句为：xiao = "assignment test";
     */
    public JCTree.JCExpressionStatement makeAssignment(JCTree.JCExpression lhs, JCTree.JCExpression rhs) {
        return treeMaker.Exec(
                treeMaker.Assign(
                        lhs,
                        rhs
                )
        );
    }

    /**
     * 导入方法依赖的package包
     * @param packageName
     * @param className
     * @return
     */
    public JCTree.JCImport buildImport(String packageName, String className) {
        JCTree.JCIdent ident = treeMaker.Ident(names.fromString(packageName));
        JCTree.JCImport jcImport = treeMaker.Import(treeMaker.Select(
                ident, names.fromString(className)), false);
         meessager.printMessage(Diagnostic.Kind.NOTE,jcImport.toString());
        return jcImport;
    }

    /**
     * 导入方法依赖的package包
     * @param element  class
     * @param packageName
     * @param className
     * @return
     */
    public void addImportInfo(TypeElement element, String packageName, String className) {
        TreePath treePath = getTrees().getPath(element);
        Tree leaf = treePath.getLeaf();
        if (treePath.getCompilationUnit() instanceof JCTree.JCCompilationUnit && leaf instanceof JCTree) {
            JCTree.JCCompilationUnit jccu = (JCTree.JCCompilationUnit) treePath.getCompilationUnit();

            for (JCTree jcTree : jccu.getImports()) {
                if (jcTree != null && jcTree instanceof JCTree.JCImport) {
                    JCTree.JCImport jcImport = (JCTree.JCImport) jcTree;
                    if (jcImport.qualid != null && jcImport.qualid instanceof JCTree.JCFieldAccess) {
                        JCTree.JCFieldAccess jcFieldAccess = (JCTree.JCFieldAccess) jcImport.qualid;
                        try {
                            if (packageName.equals(jcFieldAccess.selected.toString()) && className.equals(jcFieldAccess.name.toString())) {
                                return;
                            }
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            java.util.List<JCTree> trees = new ArrayList<>();
            trees.addAll(jccu.defs);
            JCTree.JCImport jcImport = buildImport(packageName,className);
            if (!trees.contains(jcImport)) {
                trees.add(0, jcImport);
            }
            jccu.defs = List.from(trees);
        }
    }


    public JavacTrees getTrees() {
        return trees;
    }

    public void setTrees(JavacTrees trees) {
        this.trees = trees;
    }
}
