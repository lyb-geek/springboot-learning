package com.gitee.lybgeek.scan.selector;

import com.gitee.lybgeek.scan.factory.HelloSvcBeanFactoryPostProcessor;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class HelloImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{HelloSvcBeanFactoryPostProcessor.class.getName()};
    }
}
