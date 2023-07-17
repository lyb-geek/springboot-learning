package com.github.lybgeek.common.core;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.github.lybgeek.common.event.DataSyncTriggerEvent;
import com.github.lybgeek.common.property.DataSyncTriggerProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class BaseDataSyncTrigger implements DataSyncTrigger, ApplicationContextAware {
    protected ApplicationContext applicationContext;
    
    protected final DataSyncTriggerProperty dataSyncTriggerProperty;


    @Override
    public void broadcast(Object data) {
        DataSyncTriggerEvent dataSyncTriggerEvent = new DataSyncTriggerEvent(data);
        applicationContext.publishEvent(dataSyncTriggerEvent);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private Collection<DataSyncTriggerCallBack> listDataSyncTriggerCallBacks(){
        try {
            Map<String, DataSyncTriggerCallBack> dataSyncTriggerCallBackMap = applicationContext.getBeansOfType(DataSyncTriggerCallBack.class);
            return Collections.unmodifiableList(dataSyncTriggerCallBackMap.values().stream().collect(Collectors.toList()));
        } catch (BeansException e) {

        }

        return Collections.emptyList();
    }
    
    public void callBack(Object data){
        Collection<DataSyncTriggerCallBack> dataSyncTriggerCallBacks = listDataSyncTriggerCallBacks();
        if(CollectionUtil.isNotEmpty(dataSyncTriggerCallBacks)){
            if(dataSyncTriggerProperty.isTriggerCallBackAsync()){
                callbackAsync(data, dataSyncTriggerCallBacks);
            }else{
                callbackSync(data, dataSyncTriggerCallBacks);
            }
          
        }
    }

    private  void callbackSync(Object data, Collection<DataSyncTriggerCallBack> dataSyncTriggerCallBacks) {
        for (DataSyncTriggerCallBack dataSyncTriggerCallBack : dataSyncTriggerCallBacks) {
            dataSyncTriggerCallBack.execute(data);
        }
    }

    private  void callbackAsync(Object data, Collection<DataSyncTriggerCallBack> dataSyncTriggerCallBacks) {
        for (DataSyncTriggerCallBack dataSyncTriggerCallBack : dataSyncTriggerCallBacks) {
            ThreadUtil.execAsync(()->{
                dataSyncTriggerCallBack.execute(data);
            });
        }
    }
}
