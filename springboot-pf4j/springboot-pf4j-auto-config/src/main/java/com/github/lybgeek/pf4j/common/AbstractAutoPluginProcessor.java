package com.github.lybgeek.pf4j.common;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic.Kind;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Set;

/**
 * 抽象 处理器
 *
 * @author L.cm
 */
public abstract class AbstractAutoPluginProcessor extends AbstractProcessor {

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}

	/**
	 * AutoService 注解处理器
	 *
	 * @param annotations 注解 getSupportedAnnotationTypes
	 * @param roundEnv    扫描到的 注解新
	 * @return 是否完成
	 */
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		try {
			return processImpl(annotations, roundEnv);
		} catch (Exception e) {
			fatalError(e);
			return false;
		}
	}

	protected abstract boolean processImpl(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv);

	/**
	 * 判断为类
	 *
	 * @param e Element
	 * @return {boolean}
	 */
	protected boolean isClass(Element e) {
		ElementKind kind = e.getKind();
		return kind == ElementKind.CLASS;
	}

	/**
	 * 判断为类或者接口
	 *
	 * @param e Element
	 * @return {boolean}
	 */
	protected boolean isClassOrInterface(Element e) {
		ElementKind kind = e.getKind();
		return kind == ElementKind.CLASS || kind == ElementKind.INTERFACE;
	}

	/**
	 * 获取注解，支持组合注解
	 *
	 * @param elementUtils       elementUtils
	 * @param e                  Element
	 * @param annotationFullName annotationFullName
	 * @return {boolean}
	 */
	protected AnnotationMirror getAnnotation(Elements elementUtils, Element e, String annotationFullName) {
		List<? extends AnnotationMirror> annotationList = elementUtils.getAllAnnotationMirrors(e);
		for (AnnotationMirror annotation : annotationList) {
			// 如果是对于的注解
			if (isAnnotation(annotationFullName, annotation)) {
				return annotation;
			}
			// 处理组合注解
			Element element = annotation.getAnnotationType().asElement();
			// 如果是 java 元注解，继续循环
			if (element.toString().startsWith("java.lang")) {
				continue;
			}
			// 递归处理 组合注解
			return getAnnotation(elementUtils, element, annotationFullName);
		}
		return null;
	}

	/**
	 * 判断是相同的注解，支持组合注解
	 *
	 * @param elementUtils       elementUtils
	 * @param e                  Element
	 * @param annotationFullName annotationFullName
	 * @return {boolean}
	 */
	protected boolean isAnnotation(Elements elementUtils, Element e, String annotationFullName) {
		List<? extends AnnotationMirror> annotationList = elementUtils.getAllAnnotationMirrors(e);
		for (AnnotationMirror annotation : annotationList) {
			// 如果是对于的注解
			if (isAnnotation(annotationFullName, annotation)) {
				return true;
			}
			// 处理组合注解
			Element element = annotation.getAnnotationType().asElement();
			// 如果是 java 元注解，继续循环
			if (element.toString().startsWith("java.lang")) {
				continue;
			}
			// 递归处理 组合注解
			if (isAnnotation(elementUtils, element, annotationFullName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否同一个注解
	 *
	 * @param annotationFullName annotationFullName
	 * @param annotation         AnnotationMirror
	 * @return {boolean}
	 */
	protected boolean isAnnotation(String annotationFullName, AnnotationMirror annotation) {
		return annotationFullName.equals(annotation.getAnnotationType().toString());
	}

	/**
	 * 获取属性的名称
	 *
	 * @param element Element
	 * @return {String}
	 */
	protected String getQualifiedName(Element element) {
		if (element instanceof QualifiedNameable) {
			return ((QualifiedNameable) element).getQualifiedName().toString();
		}
		return element.toString();
	}

	protected void log(String msg) {
		if (processingEnv.getOptions().containsKey("debug")) {
			processingEnv.getMessager().printMessage(Kind.NOTE, msg);
		}
	}

	protected void error(String msg, Element element) {
		processingEnv.getMessager().printMessage(Kind.ERROR, msg, element);
	}

	protected void error(String msg, Element element, AnnotationMirror annotation) {
		processingEnv.getMessager().printMessage(Kind.ERROR, msg, element, annotation);
	}

	protected void fatalError(Exception e) {
		// We don't allow exceptions of any kind to propagate to the compiler
		StringWriter writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));
		fatalError(writer.toString());
	}

	protected void fatalError(String msg) {
		processingEnv.getMessager().printMessage(Kind.ERROR, "FATAL ERROR: " + msg);
	}

}
