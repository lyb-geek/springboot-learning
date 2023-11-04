package com.github.lybgeek.groovy.core.factory.support;

import com.github.lybgeek.groovy.core.factory.GroovyFactory;

public class DefaultGroovyFactory<T> implements GroovyFactory<T> {

    /**
     * Returns a new implementation of ZuulFilter as specified by the provided 
     * Class. The Class is instantiated using its nullary constructor.
     * 
     * @param clazz the Class to instantiate
     * @return A new instance of T
     */
    @Override
    public T newInstance(Class clazz) throws InstantiationException, IllegalAccessException {
        return (T) clazz.newInstance();
    }

}