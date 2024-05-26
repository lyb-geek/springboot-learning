package com.github.lybgeek.pipeline.test.valve;


import com.github.lybgeek.valve.context.ValveContext;
import com.github.lybgeek.valve.support.AbstractValve;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class FirstValve extends AbstractValve {
    @Override
    public void doInvoke(ValveContext context) {
        String requestId = "lybgeek-" + UUID.randomUUID().toString();
        System.out.println("第一道阀门: requestId-->【" + requestId + "】");
        Map<String,Object> request = context.getRequest();
        request.put("source",FirstValve.class.getSimpleName());
        context.setRequest(request);
        context.setRequestId(requestId);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
