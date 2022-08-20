package com.github.lybgeek.bean.selector;


import com.github.lybgeek.bean.annotation.LybGeekEnableAutoConfiguration;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

import java.util.List;

public class LybGeekAutoConfigurationImportSelector implements DeferredImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        List<String> candidateConfigurations = SpringFactoriesLoader.loadFactoryNames(LybGeekEnableAutoConfiguration.class, null);
        Assert.notEmpty(candidateConfigurations, "No auto configuration classes found in META-INF/spring.factories. If you "
                + "are using a custom packaging, make sure that file is correct.");
        return candidateConfigurations.toArray(new String[0]);
    }
}
