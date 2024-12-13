package com.github.lybgeek.pf4j.processor;

import com.github.lybgeek.pf4j.annotation.AutoPluginConfig;
import com.github.lybgeek.pf4j.common.AbstractAutoPluginProcessor;
import com.github.lybgeek.pf4j.model.PluginConfig;
import com.github.lybgeek.pf4j.util.StringUtils;
import com.google.auto.service.AutoService;


import javax.annotation.processing.*;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Processes {@link AutoPluginConfig} annotations and generates the plugin properties
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("*")
@SupportedOptions("debug")
public class AutoPluginConfigProcessor extends AbstractAutoPluginProcessor {

  /**
   * 处理的注解 @AutoPluginConfig
   */
  private static final String AUTO_PLUGIN_ANNOTATION = "com.github.lybgeek.pf4j.annotation.AutoPluginConfig";

  /**
   * 数据承载
   */
  private final Map<String, PluginConfig> pluginConfigMap = new LinkedHashMap<>();

  private final static String PLUGIN_RESOURCE_LOCATION = "plugin.properties";



  /**
   * 元素辅助类
   */
  private Elements elementUtils;

  @Override
  public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    elementUtils = processingEnv.getElementUtils();
  }

  @Override
  protected boolean processImpl(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    if (roundEnv.processingOver()) {
      // 1. 生成 plugin.properties
      generatePluginFiles();
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
      if (isAnnotation(elementUtils, typeElement, AUTO_PLUGIN_ANNOTATION)) {
        log("Found @AutoPluginConfig Element: " + typeElement.toString());
        AutoPluginConfig autoPluginConfig = typeElement.getAnnotation(AutoPluginConfig.class);
        PluginConfig pluginConfig = buildPluginConfig(autoPluginConfig);
        String pluginName = typeElement.getQualifiedName().toString();
        if(StringUtils.isBlank(pluginConfig.getId())){
          pluginConfig.setId(pluginName);
        }

        // 只会存在一个插件配置
        if (pluginConfigMap.containsKey(AUTO_PLUGIN_ANNOTATION)) {
          return;
        }

        pluginConfigMap.put(AUTO_PLUGIN_ANNOTATION, pluginConfig);
      }
    }
  }

  @Override
  public void log(String msg) {
      processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
  }

  private PluginConfig buildPluginConfig(AutoPluginConfig autoPluginConfig){
    PluginConfig pluginConfig = new PluginConfig();
    pluginConfig.setId(autoPluginConfig.id());
    pluginConfig.setDescription(autoPluginConfig.description());
    pluginConfig.setVersion(autoPluginConfig.version());
    pluginConfig.setProvider(autoPluginConfig.provider());
    pluginConfig.setRequires(autoPluginConfig.requires());
    pluginConfig.setClassName(autoPluginConfig.className());
    pluginConfig.setDependencies(autoPluginConfig.dependencies());
    return pluginConfig;

  }

  private void generatePluginFiles() {
    if (pluginConfigMap.isEmpty()) {
      return;
    }
    Filer filer = processingEnv.getFiler();
    try {
      // plugin.properties 配置
      Map<String, String> allPluginFields = new LinkedHashMap<>();
      // 1. 用户手动配置项目下的 plugin.properties 文件
      try {
        FileObject existingPluginFile = filer.getResource(StandardLocation.CLASS_OUTPUT, "", PLUGIN_RESOURCE_LOCATION);
        // 查找是否已经存在 plugin.properties
        log("Looking for existing plugin.properties file at " + existingPluginFile.toUri());
        Map<String, String> existingPluginFields = PluginConfigFiles.readPluginFile(existingPluginFile, elementUtils);
        allPluginFields.putAll(existingPluginFields);
        log("Existing plugin.properties entries: " + allPluginFields);
      } catch (Exception e) {
        log("plugin.properties resource file not found.");
      }

      if (allPluginFields.isEmpty()) {
        PluginConfig pluginConfig = pluginConfigMap.get(AUTO_PLUGIN_ANNOTATION);
        allPluginFields.putAll(pluginConfig.convertToMap());
      }



      log("plugin.properties file contents: " + allPluginFields);
      FileObject pluginFile = filer.createResource(StandardLocation.CLASS_OUTPUT, "", PLUGIN_RESOURCE_LOCATION);
      try (OutputStream out = pluginFile.openOutputStream()) {
        PluginConfigFiles.writePluginFile(allPluginFields, out);
      }
    } catch (IOException e) {
      fatalError(e);
    }
  }


}
