package com.github.lybgeek.scope.event;


import org.springframework.context.ApplicationEvent;

public class RefreshBeanEvent extends ApplicationEvent {


    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public RefreshBeanEvent(Object source) {
        super(source);
    }
}
