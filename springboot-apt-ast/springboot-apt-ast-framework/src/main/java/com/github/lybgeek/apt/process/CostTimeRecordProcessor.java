package com.github.lybgeek.apt.process;


import com.github.lybgeek.apt.annotation.CostTimeRecoder;
import com.github.lybgeek.ast.CostTimeRecordAstTranslator;
import com.github.lybgeek.log.factory.LogFactory;
import com.github.lybgeek.log.model.LogDTO;
import com.github.lybgeek.log.service.LogService;
import com.google.auto.service.AutoService;
import com.sun.source.util.Trees;
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
import javax.lang.model.util.Elements;
import java.util.Collections;
import java.util.Set;


@AutoService(Processor.class)
@SupportedOptions("debug")
public class CostTimeRecordProcessor extends AbstractComponentProcessor {


    /**
     * 元素辅助类
     */
    private Elements elementUtils;

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
        elementUtils = processingEnv.getElementUtils();
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

        if (!roundEnv.processingOver()) {
            elements.stream() .filter(element -> element instanceof ExecutableElement)
                    .map(element -> (ExecutableElement) element)
                    .forEach(method -> {
                        TypeElement typeElement = (TypeElement)method.getEnclosingElement();
                        JCTree.JCClassDecl tree = trees.getTree(typeElement);
                        JCTree.JCMethodDecl methodDecl = trees.getTree(method);
                        CostTimeRecordAstTranslator costTimeRecordAstTranslator = new CostTimeRecordAstTranslator(treeMaker,names,meessager,tree,methodDecl);
                        costTimeRecordAstTranslator.setTrees(trees);
                        // 导入引用类,如果不配置import，则方法调用，需配置全类路径，
                        // 比如LogFactory.getLogger(),如果没导入LogFactory，则方法需写成com.github.lybgeek.log.factory.LogFactory.getLogger
                        // 配置后，仅需写成LogFactory.getLogger即可
                        costTimeRecordAstTranslator.addImportInfo(typeElement, LogFactory.class.getPackage().getName(),LogFactory.class.getSimpleName());
                        costTimeRecordAstTranslator.addImportInfo(typeElement,LogDTO.class.getPackage().getName(),LogDTO.class.getSimpleName());
//                        costTimeRecordAstTranslator.addImportInfo(typeElement, LogService.class.getPackage().getName(),LogService.class.getSimpleName());

                        tree.accept(costTimeRecordAstTranslator);

                    });
        }



        return false;
    }


    private String getPackageName(TypeElement typeElement) {
        return elementUtils.getPackageOf(typeElement).getQualifiedName()
                .toString();
    }




}




