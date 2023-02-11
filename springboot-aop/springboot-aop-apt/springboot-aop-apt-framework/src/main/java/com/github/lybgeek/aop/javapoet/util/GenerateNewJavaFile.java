package com.github.lybgeek.aop.javapoet.util;


import com.github.lybgeek.aop.javapoet.model.BeanInfo;
import com.squareup.javapoet.*;

import javax.annotation.processing.Filer;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import java.io.IOException;
import java.util.*;

public final class GenerateNewJavaFile {

    private GenerateNewJavaFile(){

    }

    private static Map<String, List<BeanInfo>> beanInfoCache = new HashMap<>();

    public static void generate(Filer filer,Elements elementUtils, Set<? extends Element> elements){
        for (Element element : elements) {
            String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
            if(isMethod(element)){
                ExecutableElement methodElement = (ExecutableElement) element;
                String methodName = methodElement.getSimpleName().toString();
                //获取方法的父级元素，即ClassName
                String className = methodElement.getEnclosingElement().toString();
                String simpleClassName = methodElement.getEnclosingElement().getSimpleName().toString();
                String returnClassTypeName = methodElement.getReturnType().toString();

                Map<String,String> parameterInfos = new LinkedHashMap<>();
                for (VariableElement parameter : methodElement.getParameters()) {
                    String parameterName = parameter.getSimpleName().toString();
                    String parameterNameClassName = parameter.asType().toString();
                    parameterInfos.put(parameterName,parameterNameClassName);
                }

                String key = packageName + "." + className;

                List<BeanInfo> beanInfos = beanInfoCache.get(key);
                BeanInfo beanInfo = BeanInfo.buider()
                        .setPackageName(packageName)
                        .setClassName(className)
                        .setSimpleClassName(simpleClassName)
                        .setMethodName(methodName)
                        .setParameterInfos(parameterInfos)
                        .setReturnClassTypeName(returnClassTypeName);

                if (beanInfos == null) {
                    beanInfos = new ArrayList<>();

                    beanInfos.add(beanInfo);
                    beanInfoCache.put(key, beanInfos);
                } else {
                    beanInfos.add(beanInfo);
                }
            }

        }


        if (!beanInfoCache.isEmpty()) {
            for (List<BeanInfo> infos : beanInfoCache.values()) {
                try {
                    createFile(filer,infos);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private static void createFile(Filer filer,List<BeanInfo> infos) throws IOException {
        BeanInfo info = infos.get(0);

        // 生成的文件名(类名)
        String className = info.getSimpleClassName()+ "CostTimeRecoder";;

        // 方法
        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder(info.getMethodName())
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.bestGuess(info.getReturnClassTypeName()));

        Map<String, String> parameterInfos = info.getParameterInfos();
        parameterInfos.forEach((parameterName,parameterNameClassName) ->{
            // 方法参数
            ParameterSpec parameterSpec = ParameterSpec.builder(
                    ClassName.bestGuess(parameterNameClassName), parameterName)
                    .build();
            methodSpecBuilder.addParameter(parameterSpec);
        });


        // 给方法添加代码块
        methodSpecBuilder.addStatement("$T startTime = System.currentTimeMillis()",long.class)
                .addStatement("System.out.println(\"hello ->\" + message)")
                .addStatement("$T costTime = System.currentTimeMillis() - startTime",long.class)
                .addStatement("System.out.println(\"costTime : \" + costTime + \"ms\")")
                .addStatement("return \"hello : \" + message");

        // 类
        TypeSpec typeSpec = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .superclass(ClassName.bestGuess(info.getClassName()))
                .addMethod(methodSpecBuilder.build())
                .build();



        // 生成文件
        JavaFile.builder(info.getPackageName(), typeSpec)
                .build()
                .writeTo(filer);
    }

    public static boolean isMethod(Element e) {
        ElementKind kind = e.getKind();
        return kind == ElementKind.METHOD;
    }

}
