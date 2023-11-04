package com.github.lybgeek.groovy.core.loader;

import java.io.File;
import java.util.List;

/**
 *  It compiles, loads from a File, and checks if source code changed.
 *
 */
public interface GroovyLoader<T> {
    /**
     * From a file this will read the classes source code, compile it
     * a true response means that it was successful.
     *
     * @param file the file to load
     * @return true if the object in file successfully read, compiled, verified
     */
    boolean putObject(File file);

    /**
     * Load and cache objects by className.
     *
     * @param classNames The class names to load
     * @return List of the loaded objects
     * @throws Exception If any specified filter fails to load, this will abort. This is a safety mechanism so we can
     * prevent running in a partially loaded state.
     */
    List<T> putObjectsForClasses(String[] classNames) throws Exception;


    T putObjectForClassName(String className) throws Exception;


}
