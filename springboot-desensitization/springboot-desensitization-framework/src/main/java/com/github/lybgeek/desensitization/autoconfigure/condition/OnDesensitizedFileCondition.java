package com.github.lybgeek.desensitization.autoconfigure.condition;


import com.github.lybgeek.desensitization.property.DesensitizedProperties;
import com.github.lybgeek.desensitization.util.SensitiveWordUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

public class OnDesensitizedFileCondition implements Condition {


    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        if(metadata.isAnnotated(ConditionalOnDesensitizedFile.class.getName())){
            return matchConditionalOnDesensitizedFile(context);
        }else if(metadata.isAnnotated(ConditionalOnMissingDesensitizedFile.class.getName())){
            return matchConditionalOnMissingDesensitizedFile(context);
        }
        return false;
    }

    private boolean matchConditionalOnMissingDesensitizedFile(ConditionContext context){
        String filePath = context.getEnvironment().getProperty(DesensitizedProperties.PREFIX + "." + "filepath");
        String dir = context.getEnvironment().getProperty(DesensitizedProperties.PREFIX + "." + "dir");
        return StringUtils.isEmpty(filePath) && StringUtils.isEmpty(dir);
    }

    private boolean matchConditionalOnDesensitizedFile(ConditionContext context){
        String filePath = context.getEnvironment().getProperty(DesensitizedProperties.PREFIX + "." + "filepath");
        if(StringUtils.hasText(filePath)){
            SensitiveWordUtils.loadByClassPathFile(filePath);
        }

        String dir = context.getEnvironment().getProperty(DesensitizedProperties.PREFIX + "." + "dir");
        if(StringUtils.hasText(dir)){
            SensitiveWordUtils.init(dir);
        }

        return !matchConditionalOnMissingDesensitizedFile(context);
    }
}
