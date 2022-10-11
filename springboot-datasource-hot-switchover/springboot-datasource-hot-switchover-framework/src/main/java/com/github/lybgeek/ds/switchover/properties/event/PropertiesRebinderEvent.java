package com.github.lybgeek.ds.switchover.properties.event;

import org.springframework.context.ApplicationEvent;

import java.util.Set;


public class PropertiesRebinderEvent extends ApplicationEvent {

    private Set<String> keys;

    public PropertiesRebinderEvent(Set<String> keys) {
        // Backwards compatible constructor with less utility (practically no use at all)
        this(keys, keys);
    }

    public PropertiesRebinderEvent(Object context, Set<String> keys) {
        super(context);
        this.keys = keys;
    }

    /**
     * @return The keys.
     */
    public Set<String> getKeys() {
        return this.keys;
    }

}
