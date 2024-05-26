package com.github.lybgeek.pipeline.test.valve;


import com.github.lybgeek.valve.context.ValveContext;
import com.github.lybgeek.valve.support.AbstractValve;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class ThirdValve extends AbstractValve {
    @Override
    public void doInvoke(ValveContext context) {
        String requestId = "lybgeek-" + UUID.randomUUID().toString();
        if(context.getRequestId() != null){
            requestId = context.getRequestId();
        }
        System.out.println("第三道阀门: requestId-->【" + requestId + "】");

            Map<String,Object> request = context.getRequest();
            if(request.containsKey("source")){
                String value =  request.get("source") + "|" + ThirdValve.class.getSimpleName();
                request.put("source",value);
            }
        }

    @Override
    public int getOrder() {
        return 3;
    }
}
