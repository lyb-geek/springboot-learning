package com.github.lybgeek.valve.support;


import com.github.lybgeek.valve.Valve;
import com.github.lybgeek.valve.context.ValveContext;

public abstract class AbstractValve implements Valve {

    protected Valve nextValve;

    @Override
    public Valve getNextValve() {
        return nextValve;
    }

    @Override
    public void setNextValve(Valve next) {
       this.nextValve = next;
    }

    @Override
    public void invoke(ValveContext context) {
         doInvoke(context);
         if(nextValve!=null){
             nextValve.invoke(context);
         }
    }

    public abstract void doInvoke(ValveContext context);


}
