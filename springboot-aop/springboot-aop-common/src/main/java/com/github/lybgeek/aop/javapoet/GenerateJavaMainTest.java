package com.github.lybgeek.aop.javapoet;


import com.github.lybgeek.aop.aspect.EchoAspect;
import com.squareup.javapoet.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static javax.lang.model.element.Modifier.PUBLIC;

public class GenerateJavaMainTest {

    public static void main(String[] args) {
          generete();
    }

    private static void generete(){
        //创建函数的参数
        ParameterSpec parameterSpec = ParameterSpec.builder(String.class, "username")
                .build();
        

        //创建函数
        //$T  代指 TypeName 类
        //$N  代指变量或方法名称
        //$S  代指字符串
        MethodSpec methodSpec = MethodSpec.methodBuilder("sayHello")
                 .addModifiers(PUBLIC)
                 .addParameter(parameterSpec)
                 .addStatement("$T startTime = System.currentTimeMillis()",long.class)
                 .addStatement("new $T().before(null)", EchoAspect.class)
                 .addStatement("System.out.println(\"hello ->\" + username);")
                 .addStatement("$T costTime = System.currentTimeMillis() - startTime",long.class)
                 .addStatement("System.out.println(\"costTime : \" + costTime + \"ms\");")
                .build();



        //创建类
        TypeSpec typeSpec = TypeSpec.classBuilder("HelloService")
                .addModifiers(PUBLIC)
                .addMethod(methodSpec)
                .build();

        //com.github.lybgeek.aop.javapoet 是包名
        JavaFile file = JavaFile.builder("com.github.lybgeek.aop.javapoet", typeSpec).build();
        try {
//            file.writeTo(System.out);
            file.writeTo(new File(getProjectPath() + "/src/main/java"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getProjectPath() {
        String basePath = GenerateJavaMainTest.class.getResource("").getPath();
        return basePath.substring(0, basePath.indexOf("/target"));
    }
}
