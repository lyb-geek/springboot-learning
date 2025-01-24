package com.github.lybgeek.chain.registrar;


import com.github.lybgeek.chain.annotation.EnableChain;
import com.github.lybgeek.chain.scanner.ChainClassPathBeanDefinitionScanner;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Filter;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class ChainRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(EnableChain.class.getName());
        String[] basePackages = (String[]) annotationAttributes.get("basePackages");
        System.out.println("basePackages:" + Arrays.toString(basePackages));

        ChainClassPathBeanDefinitionScanner chainClassPathBeanDefinitionScanner = new ChainClassPathBeanDefinitionScanner(registry);
        chainClassPathBeanDefinitionScanner.addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
        chainClassPathBeanDefinitionScanner.scan(basePackages);



    }
}
