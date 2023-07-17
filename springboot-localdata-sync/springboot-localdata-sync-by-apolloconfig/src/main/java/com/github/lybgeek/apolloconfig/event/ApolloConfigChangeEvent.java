package com.github.lybgeek.apolloconfig.event;


import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import org.springframework.context.ApplicationEvent;

public class ApolloConfigChangeEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public ApolloConfigChangeEvent(ConfigChangeEvent source) {
        super(source);
    }
}
