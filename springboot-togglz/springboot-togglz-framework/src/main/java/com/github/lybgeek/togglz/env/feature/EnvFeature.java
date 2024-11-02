package com.github.lybgeek.togglz.env.feature;


import org.togglz.core.Feature;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.annotation.Label;
import org.togglz.core.context.FeatureContext;

public enum EnvFeature implements Feature {

    @Label("生产环境")
    PROD,

    @Label("预发布环境")
    UAT,

    @Label("测试环境")
    TEST,


    @EnabledByDefault
    @Label("开发环境")
    DEV;

    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }

    public static EnvFeature getEnvFeature(String env){
        EnvFeature[] envFeatures = EnvFeature.values();
        for (EnvFeature envFeature : envFeatures) {
            if(envFeature.name().equalsIgnoreCase(env)){
                return envFeature;
            }
        }
        throw new IllegalArgumentException("env参数不合法");
    }
}
