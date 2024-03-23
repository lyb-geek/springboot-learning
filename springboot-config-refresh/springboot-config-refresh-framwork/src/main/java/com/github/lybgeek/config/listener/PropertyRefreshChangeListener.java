package com.github.lybgeek.config.listener;


import cn.hutool.core.collection.CollectionUtil;
import com.github.lybgeek.config.binder.PropertyRebinder;
import com.github.lybgeek.config.model.RefreshProperty;
import com.github.lybgeek.config.property.RefreshConfigProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.context.environment.EnvironmentManager;
import org.springframework.context.event.EventListener;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@Slf4j
public class PropertyRefreshChangeListener implements SmartInitializingSingleton {

    private final ObjectProvider<List<PropertyRebinder>> objectProvider;

    private List<PropertyRebinder> propertyRebinders;

    private final RefreshConfigProperty refreshConfigProperty;

    private ExecutorService executorService;

    @Autowired
    private EnvironmentManager environmentManager;

    @EventListener(EnvironmentChangeEvent.class)
    public void listener(EnvironmentChangeEvent event){
        if(CollectionUtils.isEmpty(propertyRebinders)){
            return;
        }
        RefreshProperty refreshProperty = get(event.getKeys());
        propertyRebinders.forEach(propertyRebinder -> run(() -> propertyRebinder.binder(refreshProperty)));

    }

    private RefreshProperty get(Set<String> keys){
        RefreshProperty refreshProperty = new RefreshProperty();
        refreshProperty.setRefreshKeys(keys);
        if(CollectionUtil.isNotEmpty(keys)){
            Map<String,Object> newConfigProperties = new HashMap<>();
            for (String key : keys) {
               newConfigProperties.put(key,environmentManager.getProperty(key));
            }

            refreshProperty.setNewConfigProperties(newConfigProperties);
        }

        return refreshProperty;
    }

    @Override
    public void afterSingletonsInstantiated() {
        try {
            propertyRebinders = objectProvider.getIfAvailable();
            assert propertyRebinders != null;
            executorService = Executors.newFixedThreadPool(propertyRebinders.size(), r -> new Thread(r, "PropertyRebinder"));
        } catch (BeansException e) {
           log.error("PropertyRebinder init error");
        }
    }

    private void run(Runnable runnable){
        if(refreshConfigProperty.isRefreshAsync() && executorService != null){
            executorService.execute(()-> doExecute(runnable));
        }else{
             doExecute(runnable);
        }
    }

    private void doExecute(Runnable runnable){
        try {
            runnable.run();
        } catch (Exception e) {
            log.error("PropertyRebinder run error",e);
        }
    }
}
