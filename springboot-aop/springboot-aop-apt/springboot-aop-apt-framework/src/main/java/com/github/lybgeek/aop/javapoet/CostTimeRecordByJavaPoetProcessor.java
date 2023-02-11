package com.github.lybgeek.aop.javapoet;


import com.github.lybgeek.aop.annotation.CostTimeRecoder;
import com.github.lybgeek.aop.apt.AbstractComponentProcessor;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.*;

import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 参考：{@link https://github.com/shiraji/kenkenpa/blob/master/kenkenpa-compiler/src/main/java/com/github/shiraji/kenkenpa/compiler/KenKenPaProcessor.java }
 */
@AutoService(Processor.class)
@SupportedOptions("debug")
public class CostTimeRecordByJavaPoetProcessor extends AbstractComponentProcessor {

    private static final String GENERATE_CLASSNAME_PREFIX = "LybGeek";

    private static final String GENERATE_CLASSNAME_SUFFIX = "CostTimeRecord";

    /**
     * 元素辅助类
     */
    private Elements elementUtils;

    /**
     * 文件创建工具类
     */
    private Filer filer;







    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
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
                    TypeSpec.Builder typeSpecBuilder = createClassTypeSpec(typeElement);
                    writeJavaFile(typeElement, typeSpecBuilder);
                });

        return false;
    }

    private void writeJavaFile(TypeElement typeElement, TypeSpec.Builder typeSpecBuilder) {
        JavaFile javaFile = JavaFile
                .builder(getPackageName(typeElement), typeSpecBuilder.build())
                .addFileComment("Generated code by LYB-GEEK!")
                .build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            fatalError("Unable to write Java file for type "  + typeElement + ", cause : " + e.getMessage());
        }
    }

    private TypeSpec.Builder createClassTypeSpec(TypeElement typeElement) {
        TypeSpec.Builder typeSpecBuilder = createGenerateClassSpec(typeElement);
        for (Element enclosedElement : typeElement.getEnclosedElements()) {
            if (enclosedElement.getKind() != ElementKind.METHOD) {
                continue;
            }
            addMethod(typeSpecBuilder, enclosedElement);
        }


        addGenerateMethods(typeSpecBuilder, typeElement);
        return typeSpecBuilder;
    }

    private TypeSpec.Builder createGenerateClassSpec(TypeElement typeElement) {
        return TypeSpec
                .classBuilder(generateClassName(typeElement))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .superclass(ClassName.get(typeElement));
    }

    private String generateClassName(TypeElement typeElement) {
        return GENERATE_CLASSNAME_PREFIX + getClassName(typeElement, getPackageName(typeElement)) + GENERATE_CLASSNAME_SUFFIX;
    }

    private static String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen)
                .replace('.', '$');
    }

    private void addMethod(TypeSpec.Builder typeSpecBuilder, Element element2) {
        MethodSpec.Builder overrivedSuperMethod = overrivedSuperMethod((ExecutableElement)element2);
        typeSpecBuilder.addMethod(overrivedSuperMethod.build());
    }

    private MethodSpec.Builder overrivedSuperMethod(ExecutableElement executableElement) {
        MethodSpec.Builder overrivedSuperMethodSpec = MethodSpec.overriding(executableElement).addModifiers(Modifier.FINAL);
        overrivedSuperMethodSpec = overrivedSuperMethodSpec
                .addStatement("$T startTime = System.currentTimeMillis()",long.class);

        boolean hasReturnValue = executableElement.getReturnType().getKind() != TypeKind.VOID;
        overrivedSuperMethodSpec = addSuperMethodCall(executableElement, overrivedSuperMethodSpec, hasReturnValue);

        overrivedSuperMethodSpec.addStatement("$T costTime = System.currentTimeMillis() - startTime",long.class)
                .addStatement("System.out.println(\"costTime : \" + costTime + \"ms\")");

        if (hasReturnValue) {
            overrivedSuperMethodSpec.addStatement("return returnValue");
        }
        return overrivedSuperMethodSpec;
    }

    private void addGenerateMethods(TypeSpec.Builder typeSpecBuilder, TypeElement typeElement) {
        typeSpecBuilder.addMethods(createConstructorSpec(typeElement));
    }

    private List<MethodSpec> createConstructorSpec(TypeElement typeElement) {
        List<MethodSpec> constructors = new ArrayList<>();
        for (Element enclosedElement : typeElement.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.CONSTRUCTOR) {
                MethodSpec.Builder constructorMethodSpec = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC);
                ExecutableElement executableElement = (ExecutableElement)enclosedElement;

                List<? extends VariableElement> parameters = executableElement.getParameters();
                if (parameters.size() > 0) {
                    for (VariableElement parameter : parameters) {
                        TypeName type = TypeName.get(parameter.asType());
                        String name = parameter.getSimpleName().toString();
                        Set<Modifier> parameterModifiers = parameter.getModifiers();
                        ParameterSpec.Builder parameterBuilder = ParameterSpec.builder(type, name)
                                .addModifiers(parameterModifiers.toArray(new Modifier[parameterModifiers.size()]));
                        for (AnnotationMirror mirror : parameter.getAnnotationMirrors()) {
                            parameterBuilder.addAnnotation(AnnotationSpec.get(mirror));
                        }
                        constructorMethodSpec.addParameter(parameterBuilder.build());
                    }

                    constructorMethodSpec.varargs(executableElement.isVarArgs());
                }

                for (TypeMirror thrownType : executableElement.getThrownTypes()) {
                    constructorMethodSpec.addException(TypeName.get(thrownType));
                }

                constructorMethodSpec = constructorMethodSpec.addStatement("super($L)", createSuperMethodParameterString(
                        executableElement));


                constructors.add(constructorMethodSpec.build());
            }
        }

        if (constructors.size() == 0) {
            MethodSpec.Builder constructorMethodSpec = MethodSpec.constructorBuilder();
            constructors.add(constructorMethodSpec.build());
        }

        return constructors;
    }




    private MethodSpec.Builder addSuperMethodCall(ExecutableElement executableElement, MethodSpec.Builder overrivedSuperMethodSpec, boolean hasReturnValue) {
        StringBuilder sb = new StringBuilder("super.$L($L)");
        if (hasReturnValue) {
            sb.insert(0, String.format("%s returnValue = ", executableElement.getReturnType().toString()));
        }
        overrivedSuperMethodSpec = overrivedSuperMethodSpec.addStatement(sb.toString(), executableElement
                .getSimpleName(), createSuperMethodParameterString(executableElement));
        return overrivedSuperMethodSpec;
    }

    /**
     * create super method call with parameters.
     *
     * @param executableElement a method that will override
     * @return String that contains super method call
     */
    private String createSuperMethodParameterString(ExecutableElement executableElement) {
        StringBuilder superMethodParameters = new StringBuilder();
        List<? extends VariableElement> parameters = executableElement.getParameters();
        if (parameters.size() > 0) {
            VariableElement parameter = parameters.get(0);
            superMethodParameters.append(parameter.getSimpleName());

            for (int i = 1; i < parameters.size(); i++) {
                superMethodParameters.append(",");
                superMethodParameters.append(parameters.get(i).getSimpleName());
            }
        }
        return superMethodParameters.toString();
    }

    private String getPackageName(TypeElement typeElement) {
        return elementUtils.getPackageOf(typeElement).getQualifiedName()
                .toString();
    }




}




