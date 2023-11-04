package com.github.lybgeek.groovy.core.factory;

public interface GroovyFactory<T> {
    
    /**
     * Returns an instance of the specified class.
     * 
     * @param clazz the Class to instantiate
     * @return an instance of T
     * @throws Exception if an error occurs
     */
    T newInstance(Class<?> clazz) throws Exception;
}
