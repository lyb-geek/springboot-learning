package com.github.lybgeek.common.event;


import org.springframework.context.ApplicationEvent;

public class DataSyncTriggerEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public DataSyncTriggerEvent(Object source) {
        super(source);
    }
}
