package com.github.lybgeek.valve;


import com.github.lybgeek.valve.context.ValveContext;
import org.springframework.core.Ordered;

public interface Valve extends Ordered {

    Valve getNextValve();

    void setNextValve(Valve next);

    void invoke(ValveContext context);

    default boolean isBaiscValve() {
        return false;
    }


}
