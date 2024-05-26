package com.github.lybgeek.pipeline.support;


import com.github.lybgeek.pipeline.Pipeline;
import com.github.lybgeek.valve.Valve;
import com.github.lybgeek.valve.context.ValveContext;
import org.springframework.util.Assert;

public class StandardPipeline implements Pipeline {
    /**
     * 第一个阀门
     */
    protected Valve first;

    /**
     * 最后一个阀门
     */
    protected Valve basic;


    @Override
    public void setBasic(Valve valve) {
        validateValve(valve,true);
        this.basic = valve;
    }

    @Override
    public void addValve(Valve valve) {
        validateValve(valve,false);
        if(first == null){
            this.first = valve;
            valve.setNextValve(basic);
        }else{
            Valve current = first;
            while(current != null){
               if(current.getNextValve() == basic){
                   current.setNextValve(valve);
                   valve.setNextValve(basic);
                   break;
               }
               current = current.getNextValve();
            }
        }

    }

    @Override
    public void process(ValveContext context) {
        if(first != null){
            if(context == null){
                context = new ValveContext();
            }
            first.invoke(context);
        }

    }

    public void validateValve(Valve valve,boolean isCheckBasicValve){
        Assert.notNull(valve, "valve must not be null");
        if(isCheckBasicValve){
            Assert.isTrue(valve.isBaiscValve(), "valve must be basic valve");
        }
    }
}
