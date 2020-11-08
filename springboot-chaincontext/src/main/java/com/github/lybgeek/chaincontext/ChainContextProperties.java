package com.github.lybgeek.chaincontext;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 *
 * 
 * @author linyb
 *
 */
@ConfigurationProperties(prefix = "spring.chaincontext")
public class ChainContextProperties {

    /**
     * Keys to set in chaincontext, all keys with keyPrefix will add to chaincontext while keys is empty
     */
    private List<String> keys;

    /**
     * The prefix of all keys
     */
    private String keyPrefix = "X-CHAIN-";

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
    
}
