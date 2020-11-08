package com.github.lybgeek.chaincontext;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The ChainRequestContext holds all key-value config while key is in {@link ChainContextProperties#getKeys()}
 * <p>
 * the ChainRequestContext is an extension of a ConcurrentHashMap
 * 
 * @author linyb
 *
 */
public class ChainContextHolder extends ConcurrentHashMap<String, Object> {

    private static final long serialVersionUID = -5792996927212791314L;

    protected static Class<? extends ChainContextHolder> contextClass = ChainContextHolder.class;

    protected static final ThreadLocal<? extends ChainContextHolder> THREAD_LOCAL = new InheritableThreadLocal<ChainContextHolder>() {
        @Override
        protected ChainContextHolder initialValue() {
            try {
                return contextClass.newInstance();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    };

    /**
     * Override the default ChainRequestContext
     *
     * @param clazz
     */
    public static void setContextClass(Class<? extends ChainContextHolder> clazz) {
        contextClass = clazz;
    }

    /**
     * Get the current ChainRequestContext
     * 
     * @return the current ChainRequestContext
     */
    public static final ChainContextHolder getCurrentContext() {
        return THREAD_LOCAL.get();
    }

    /**
     * Unsets the threadLocal context. Done at the end of the request.
     * 
     * @return
     */
    public void unset() {
        this.clear();
        THREAD_LOCAL.remove();
    }

    /**
     * Returns either passed value of the key, or if the value is {@code null}, the value of {@code defaultValue}.
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public Object getDefault(String key, Object defaultValue) {
        return Optional.ofNullable(get(key)).orElse(defaultValue);
    }

}
