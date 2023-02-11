package com.github.lybgeek.aop.ast;


import com.github.lybgeek.aop.annotation.CostTimeRecoder;
import com.github.lybgeek.aop.apt.AbstractComponentProcessor;
import com.google.auto.service.AutoService;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Names;

import javax.annotation.processing.*;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.Collections;
import java.util.Set;


@AutoService(Processor.class)
@SupportedOptions("debug")
public class CostTimeRecordByAstProcessor extends AbstractComponentProcessor {


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



    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        meessager = processingEnv.getMessager();
        this.trees = JavacTrees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment)processingEnv).getContext();
        this.treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);

    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(CostTimeRecoder.class.getName());
    }


    @Override
    protected boolean processImpl(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (annotations == null || annotations.isEmpty()) {
            return false;
        }


        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(CostTimeRecoder.class);
        if (elements == null || elements.isEmpty()){
            return false;
        }

        elements.stream() .filter(element -> element instanceof ExecutableElement)
                .map(element -> (ExecutableElement) element)
                .forEach(method -> {
                    TypeElement typeElement = (TypeElement)method.getEnclosingElement();
                    JCTree.JCMethodDecl jcMethodDecl = trees.getTree(method);
                    JCTree.JCClassDecl jcClassDecl = trees.getTree(typeElement);
                    CostTimeRecordTreeTranslator costTimeRecordTreeTranslator = new CostTimeRecordTreeTranslator(meessager,trees,treeMaker,names,jcMethodDecl);
                    jcClassDecl.accept(costTimeRecordTreeTranslator);

                });


        return false;
    }





}




