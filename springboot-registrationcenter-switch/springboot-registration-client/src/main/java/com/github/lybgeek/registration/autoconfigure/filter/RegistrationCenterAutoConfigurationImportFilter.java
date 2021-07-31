package com.github.lybgeek.registration.autoconfigure.filter;


import com.github.lybgeek.registration.constant.RegistrationCenterConstants;
import org.springframework.boot.autoconfigure.AutoConfigurationImportFilter;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RegistrationCenterAutoConfigurationImportFilter implements AutoConfigurationImportFilter, EnvironmentAware {

    private Environment environment;

    /**
     * 因为springboot自动装配，默认会把spring.factories的配置的类先全部加载到候选集合中，
     * 因此当我们代码配置启用nacos，则需把其他注册中心，比如eureka先从候选集合排除
     * @param autoConfigurationClasses
     * @param autoConfigurationMetadata
     * @return
     */
    @Override
    public boolean[] match(String[] autoConfigurationClasses, AutoConfigurationMetadata autoConfigurationMetadata) {
        Set<String> activeProfiles = Arrays.stream(environment.getActiveProfiles()).collect(Collectors.toSet());

        if(activeProfiles.contains(RegistrationCenterConstants.ACTIVE_PROFILE_NACOS)){
            return excludeMissMatchRegistrationAutoConfiguration(autoConfigurationClasses, new String[]{RegistrationCenterConstants.EUREKA_AUTO_CONFIGURATION_CLASSES});

        }else if (activeProfiles.contains(RegistrationCenterConstants.ACTIVE_PROFILE_EUREKA)){
            return excludeMissMatchRegistrationAutoConfiguration(autoConfigurationClasses, new String[]{RegistrationCenterConstants.NACOS_DISCOVERY_AUTO_CONFIGURATION_CLASSES,RegistrationCenterConstants.NACOS_DISCOVERY_CLIENT_AUTO_CONFIGURATION_CLASSES,RegistrationCenterConstants.NACOS_DISCOVERY_ENDPOINT_AUTO_CONFIGURATION_CLASSES});
        }


        return new boolean[0];
    }

    private boolean[] excludeMissMatchRegistrationAutoConfiguration(String[] autoConfigurationClasses,String[] needExcludeRegistrationAutoConfigurationClasse) {
        Map<String,Boolean> needExcludeRegistrationAutoConfigurationClzMap = initNeedExcludeRegistrationAutoConfigurationClzMap(needExcludeRegistrationAutoConfigurationClasse);
        boolean[] match = new boolean[autoConfigurationClasses.length];
        for (int i = 0; i < autoConfigurationClasses.length; i++) {
            String autoConfigurationClz = autoConfigurationClasses[i];
            match[i] = !needExcludeRegistrationAutoConfigurationClzMap.containsKey(autoConfigurationClz);

        }

        return match;
    }

    private Map<String,Boolean> initNeedExcludeRegistrationAutoConfigurationClzMap(String[] needExcludeRegistrationAutoConfigurationClasse){
        Map<String,Boolean> needExcludeRegistrationAutoConfigurationClzMap = new HashMap<>();
        for (String needExcludeRegistrationAutoConfigurationClz : needExcludeRegistrationAutoConfigurationClasse) {
            needExcludeRegistrationAutoConfigurationClzMap.put(needExcludeRegistrationAutoConfigurationClz,false);
        }

        return needExcludeRegistrationAutoConfigurationClzMap;

    }

    @Override
    public void setEnvironment(Environment environment) {
         this.environment = environment;
    }


}
