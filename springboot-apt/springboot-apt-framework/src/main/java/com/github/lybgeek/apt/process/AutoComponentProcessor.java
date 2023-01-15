package com.github.lybgeek.apt.process;


import com.github.lybgeek.apt.anntation.AutoComponent;
import com.github.lybgeek.apt.process.util.ComponentFiles;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;


@AutoService(Processor.class)
@SupportedOptions("debug")
public class AutoComponentProcessor extends AbstractComponentProcessor {


    /**
     * 元素辅助类
     */
    private Elements elementUtils;

    private Set<String> componentClassNames = new ConcurrentSkipListSet<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(AutoComponent.class.getName());
    }


    @Override
    protected boolean processImpl(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            generateConfigFiles();
        } else {
            processAnnotations(annotations, roundEnv);
        }
        return false;
    }

    private void processAnnotations(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // 日志 打印信息 gradle build --debug
        log(annotations.toString());
        Set<? extends Element> elementSet = roundEnv.getRootElements();
        log("All Element set: " + elementSet.toString());

        // 过滤 TypeElement
        Set<TypeElement> typeElementSet = elementSet.stream()
                .filter(this::isClassOrInterface)
                .filter(e -> e instanceof TypeElement)
                .map(e -> (TypeElement) e)
                .collect(Collectors.toSet());
        // 如果为空直接跳出
        if (typeElementSet.isEmpty()) {
            log("Annotations elementSet is isEmpty");
            return;
        }
        for (TypeElement typeElement : typeElementSet) {
            if (isAnnotation(elementUtils, typeElement, AutoComponent.class.getName())) {
                String componentClassName = typeElement.getQualifiedName().toString();
                componentClassNames.add(componentClassName);

            }
        }

    }

    private void generateConfigFiles() {
        Filer filer = processingEnv.getFiler();
            String resourceFile = ComponentFiles.DEFAULT_SERVICE_LOCATION;
            log("Working on resource file: " + resourceFile);
            try {
                SortedSet<String> allServices = new TreeSet<>();
                // 1. 存在用户手动编写的配置
                try {
                    FileObject existingFile = filer.getResource(StandardLocation.SOURCE_OUTPUT, "", resourceFile);
                    log("Looking for existing resource file at " + existingFile.toUri());
                    Set<String> oldServices = ComponentFiles.readServiceFile(existingFile, elementUtils);
                    log("Existing service entries: " + oldServices);
                    allServices.addAll(oldServices);
                } catch (IOException e) {
                    log("Resource file did not already exist.");
                }
                // 2. 增量编译
                try {
                    FileObject existingFile = filer.getResource(StandardLocation.CLASS_OUTPUT, "", resourceFile);
                    log("Looking for existing resource file at " + existingFile.toUri());
                    Set<String> oldServices = ComponentFiles.readServiceFile(existingFile, elementUtils);
                    log("Existing service entries: " + oldServices);
                    allServices.addAll(oldServices);
                } catch (IOException e) {
                    log("Resource file did not already exist.");
                }
                Set<String> newServices = new HashSet<>(componentClassNames);
                if (allServices.containsAll(newServices)) {
                    log("No new service entries being added.");
                    return;
                }
                // 3. 注解处理器新扫描出来的
                allServices.addAll(newServices);
                log("New service file contents: " + allServices);
                FileObject fileObject = filer.createResource(StandardLocation.CLASS_OUTPUT, "", resourceFile);
                try (OutputStream out = fileObject.openOutputStream()) {
                    ComponentFiles.writeServiceFile(allServices, out);
                }
                log("Write to: " + fileObject.toUri());
            } catch (IOException e) {
                fatalError("Unable to create " + resourceFile + ", " + e);
                return;
            }
        }

}
