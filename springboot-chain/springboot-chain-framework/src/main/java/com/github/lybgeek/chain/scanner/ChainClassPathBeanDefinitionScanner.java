package com.github.lybgeek.chain.scanner;


import org.apache.commons.chain.Command;
import org.apache.commons.chain.Filter;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;

public class ChainClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {
    
    
    public ChainClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }



    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata metadata = beanDefinition.getMetadata();
        String[] interfaceNames = metadata.getInterfaceNames();
        return interfaceNames.length > 0
                && Arrays.stream(interfaceNames)
                .anyMatch(interfaceName ->
                        Command.class.getName().equals(interfaceName)
                || Filter.class.getName().equals(interfaceName));
    }


}
