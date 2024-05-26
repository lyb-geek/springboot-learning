package com.github.lybgeek.autoconfigure;


import com.github.lybgeek.pipeline.Pipeline;
import com.github.lybgeek.pipeline.support.StandardPipeline;
import com.github.lybgeek.valve.Valve;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.OrderComparator;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class PipelineAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public Pipeline standardPipeline(ObjectProvider<List<Valve>> objectProvider){
         List<Valve> ifAvailable = objectProvider.getIfAvailable();
         return getPipeline(ifAvailable);
    }

    private  Pipeline getPipeline(List<Valve> ifAvailable) {
        Valve baisc = null;
        List<Valve> valves = new ArrayList<>();
        Pipeline pipeline = new StandardPipeline();
        if(!CollectionUtils.isEmpty(ifAvailable)){
            for (Valve valve : ifAvailable) {
                if(valve.isBaiscValve()){
                    baisc = valve;
                }else{
                   valves.add(valve);
                }
            }
        }

        if(baisc != null){
            pipeline.setBasic(baisc);
        }

        if(!CollectionUtils.isEmpty(valves)){
            OrderComparator.sort(valves);
            for (Valve valve : valves) {
                pipeline.addValve(valve);
            }
        }
        return pipeline;
    }
}
