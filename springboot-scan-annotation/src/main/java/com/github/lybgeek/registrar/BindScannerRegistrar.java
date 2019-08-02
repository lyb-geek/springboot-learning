package com.github.lybgeek.registrar;

import com.github.lybgeek.annotaiton.BingLogService;
import com.github.lybgeek.annotaiton.EnableBindLog;
import com.github.lybgeek.factory.LogServiceFactroyBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class BindScannerRegistrar implements ImportBeanDefinitionRegistrar,
        ResourceLoaderAware, BeanClassLoaderAware, EnvironmentAware {


    private ResourceLoader resourceLoader;

    private ClassLoader classLoader;

    private Environment environment;

    public BindScannerRegistrar() {
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        Set<String> basePackages = getBasePackages(importingClassMetadata);
        ClassPathScanningCandidateComponentProvider scanner = getScanner();
        scanner.setResourceLoader(this.resourceLoader);
        scanner.addIncludeFilter(new AnnotationTypeFilter(BingLogService.class));


        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidateComponents = scanner
                    .findCandidateComponents(basePackage);
            for (BeanDefinition candidateComponent : candidateComponents) {
                if (candidateComponent instanceof AnnotatedBeanDefinition) {
                    AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
                    AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
                    registerLogService(registry, annotationMetadata);
                }
            }
        }

    }


    protected Set<String> getBasePackages(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata
                .getAnnotationAttributes(EnableBindLog.class.getCanonicalName());

        Set<String> basePackages = new HashSet<>();

        for (String pkg : (String[]) attributes.get("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        for (Class<?> clazz : (Class[]) attributes.get("basePackageClasses")) {
            basePackages.add(ClassUtils.getPackageName(clazz));
        }

        if (basePackages.isEmpty()) {
            basePackages.add(
                    ClassUtils.getPackageName(importingClassMetadata.getClassName()));
        }
        return basePackages;
    }


    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {

            @Override
            protected boolean isCandidateComponent(
                    AnnotatedBeanDefinition beanDefinition) {
                if (beanDefinition.getMetadata().isIndependent()) {
                    if (beanDefinition.getMetadata().isInterface()
                            && beanDefinition.getMetadata()
                            .getInterfaceNames().length == 1
                            && Annotation.class.getName().equals(beanDefinition
                            .getMetadata().getInterfaceNames()[0])) {
                        try {
                            Class<?> target = ClassUtils.forName(
                                    beanDefinition.getMetadata().getClassName(),
                                    BindScannerRegistrar.this.classLoader);
                            return !target.isAnnotation();
                        } catch (Exception ex) {
                            this.logger.error(
                                    "Could not load target class: "
                                            + beanDefinition.getMetadata().getClassName(),
                                    ex);

                        }
                    }
                    return true;
                }
                return false;

            }
        };
    }


    private void registerLogService(BeanDefinitionRegistry registry,
                                     AnnotationMetadata annotationMetadata) {
        try {
            String className = annotationMetadata.getClassName();
            Class<?> beanClazz = Class.forName(className);
            if (!beanClazz.isAnnotationPresent(BingLogService.class)) {
                throw new RuntimeException("BingLogService is required!");
            }
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClazz);
            GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
            definition.getPropertyValues().add("logClz", beanClazz);
            definition.setBeanClass(LogServiceFactroyBean.class);
            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
            String beanId = org.apache.commons.lang3.StringUtils.uncapitalize(beanClazz.getSimpleName());
            registry.registerBeanDefinition(beanId, definition);

//            Field[] fields = beanClazz.getDeclaredFields();
//            if(fields != null && fields.length > 0){
//                for(Field field : fields){
//                    boolean hasAnnotation = field.isAnnotationPresent(Autowired.class);
//                    if(hasAnnotation){
//                        registryFieldBean(registry, builder, field);
//                    }else{
//                        hasAnnotation = field.isAnnotationPresent(Resource.class);
//                        if(hasAnnotation){
//                            registryFieldBean(registry, builder, field);
//                        }
//                    }
//                }
//            }
        } catch (ClassNotFoundException e) {
            log.error(
                    "Could not register target class: "
                            + annotationMetadata.getClassName(),
                    e);
        }
    }

    private void registryFieldBean(BeanDefinitionRegistry registry, BeanDefinitionBuilder builder, Field field) {
        BeanDefinitionBuilder fieldBuilder = BeanDefinitionBuilder.genericBeanDefinition(field.getClass());
        BeanDefinition beanDefinition = builder.getBeanDefinition();
        registry.registerBeanDefinition(field.getName(),beanDefinition);
    }


}
