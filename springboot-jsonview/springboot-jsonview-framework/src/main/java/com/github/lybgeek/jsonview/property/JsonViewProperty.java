package com.github.lybgeek.jsonview.property;


import cn.hutool.core.map.MapUtil;
import com.github.lybgeek.jsonview.factory.JsonViewFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Configuration
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = JsonViewProperty.PREFIX)
@Slf4j
@Role(RootBeanDefinition.ROLE_INFRASTRUCTURE)
public class JsonViewProperty {

    public static final String PREFIX = "lybgeek.jsonview";

    private boolean enabled = true;

    private boolean ignoreNullPropertyValue = true;

    private Map<String,Class<? extends JsonViewFactory>> jsonViewFactoryMap;


    public List<Class<?>> getJsonViewModelClasses(){
        if(MapUtil.isEmpty(jsonViewFactoryMap)){
            return Collections.emptyList();
        }
        List<Class<?>> jsonViewModelClasses = new ArrayList<>();
        for (String jsonViewModelClassName : jsonViewFactoryMap.keySet()) {
            try {
                Class<?> jsonViewModelClass = Class.forName(jsonViewModelClassName);
                jsonViewModelClasses.add(jsonViewModelClass);
            } catch (ClassNotFoundException e) {
               log.error("jsonViewModelClassName:{} not found",jsonViewModelClassName);
            }
        }
        return jsonViewModelClasses;
    }

}
