package com.github.lybgeek.pipeline;


import com.github.lybgeek.valve.Valve;
import com.github.lybgeek.valve.context.ValveContext;

public interface Pipeline {

    void setBasic(Valve valve);

    void addValve(Valve valve);

    void process(ValveContext context);
}
