package com.github.lybgeek.log.factory;


import com.github.lybgeek.log.service.LogService;

import java.util.*;

public final class LogFactory {

    private LogFactory(){}

    public static LogService getLogger(){
        ServiceLoader<LogService> logServices = ServiceLoader.load(LogService.class);
        Iterator<LogService> iterator = logServices.iterator();
        List<LogService> list = new ArrayList<>();
        while (iterator.hasNext()){
            list.add(iterator.next());
        }

        if(list.size() == 0){
            throw new NoSuchElementException("not found available log service, please check META-INF/services/com.github.lybgeek.log.service.LogService");
        }

        return list.stream().sorted(Comparator.comparing(LogService::order)).findFirst().get();

    }
}
