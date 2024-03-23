package com.github.lybgeek.config.event;


import org.springframework.context.ApplicationEvent;

public class PropertyRefreshedEvent extends ApplicationEvent {

    private String propertyKey;

    private Object propertyValue;


    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public PropertyRefreshedEvent(Object source,String propertyKey,Object propertyValue) {
        super(source);
        this.propertyKey = propertyKey;
        this.propertyValue = propertyValue;
    }

    public String getPropertyKey() {
        return propertyKey;
    }

    public Object getPropertyValue() {
        return propertyValue;
    }
}
