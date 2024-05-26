package com.github.lybgeek.pipeline.test.valve;


import com.github.lybgeek.valve.context.ValveContext;
import com.github.lybgeek.valve.support.AbstractValve;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class BasicValve extends AbstractValve {
    @Override
    public void doInvoke(ValveContext context) {
        String requestId = "lybgeek-" + UUID.randomUUID().toString();
        if( context.getRequestId() != null){
            requestId = context.getRequestId();
        }

        Map<String,Object> request = context.getRequest();
        String value = "";
        if(request.containsKey("source")){
            value =  String.valueOf(request.get("source"));
        }

        System.out.println("最后一道阀门: requestId-->【" + requestId + "】,该流程流经如下阀门：【" + value + "】");




    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public boolean isBaiscValve() {
        return true;
    }
}
