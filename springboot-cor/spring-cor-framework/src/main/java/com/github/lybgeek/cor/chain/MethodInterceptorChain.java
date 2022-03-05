package com.github.lybgeek.cor.chain;


import com.github.lybgeek.cor.handler.AbstarctHandler;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MethodInterceptorChain {

    private final List<AbstarctHandler> abstarctHandlers = new ArrayList<>();


    public void addHandler(AbstarctHandler handler){
        abstarctHandlers.add(handler);
    }

    public List<AbstarctHandler> getHanlders(){
        if(CollectionUtils.isEmpty(abstarctHandlers)){
            return Collections.emptyList();
        }
        AnnotationAwareOrderComparator.sort(abstarctHandlers);
        return Collections.unmodifiableList(abstarctHandlers);
    }



}
