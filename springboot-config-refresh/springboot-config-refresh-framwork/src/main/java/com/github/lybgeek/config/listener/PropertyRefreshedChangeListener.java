package com.github.lybgeek.config.listener;


import com.github.lybgeek.config.event.PropertyRefreshedEvent;
import com.github.lybgeek.config.property.RefreshConfigProperty;
import com.github.lybgeek.config.sync.PropertyRefreshedSync;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.event.EventListener;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@Slf4j
public class PropertyRefreshedChangeListener implements SmartInitializingSingleton {

    private final ObjectProvider<List<PropertyRefreshedSync>> objectProvider;

    private List<PropertyRefreshedSync> propertyRefreshedSyncs;

    private final RefreshConfigProperty refreshConfigProperty;

    private ExecutorService executorService;


    @EventListener(PropertyRefreshedEvent.class)
    public void listener(PropertyRefreshedEvent event){
        if(CollectionUtils.isEmpty(propertyRefreshedSyncs)){
            return;
        }
        propertyRefreshedSyncs.forEach(propertyRefreshedSync -> run(() -> propertyRefreshedSync.execute(event.getPropertyKey(),event.getPropertyValue())));

    }


    @Override
    public void afterSingletonsInstantiated() {
        try {
            propertyRefreshedSyncs = objectProvider.getIfAvailable();
            assert propertyRefreshedSyncs != null;
            executorService = Executors.newFixedThreadPool(propertyRefreshedSyncs.size(), r -> new Thread(r, "PropertyRebinder"));
        } catch (BeansException e) {
           log.error("propertyRefreshedSyncs init error");
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
            log.error("propertyRefreshedSyncs run error",e);
        }
    }
}
