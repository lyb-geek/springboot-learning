package com.github.lybgeek.groovy.core.loader.support;

import com.github.lybgeek.groovy.core.compiler.DynamicCodeCompiler;
import com.github.lybgeek.groovy.core.factory.GroovyFactory;
import com.github.lybgeek.groovy.core.loader.GroovyLoader;
import com.github.lybgeek.groovy.core.registry.GroovyRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class DynamicGroovyLoader<T> implements GroovyLoader<T> {
    private static final Logger LOG = LoggerFactory.getLogger(GroovyLoader.class);

    private final ConcurrentMap<String, Long> groovyClassLastModified = new ConcurrentHashMap<>();

    private final GroovyRegistry<T> groovyRegistry;

    private final DynamicCodeCompiler compiler;

    private final GroovyFactory<T> groovyFactory;

    public DynamicGroovyLoader(
            GroovyRegistry<T> groovyRegistry,
            DynamicCodeCompiler compiler,
            GroovyFactory<T> groovyFactory) {
        this.groovyRegistry = groovyRegistry;
        this.compiler = compiler;
        this.groovyFactory = groovyFactory;
    }




    /**
     * From a file this will read the object source code, compile it
     * a true response means that it was successful.
     *
     * @param file the file to load
     * @return true if the object in file successfully read, compiled, verified
     */
    @Override
    public boolean putObject(File file) {
        if (!groovyRegistry.isMutable()) {
            return false;
        }
        try {
            String sName = file.getAbsolutePath();
            if (groovyClassLastModified.get(sName) != null
                    && (file.lastModified() != groovyClassLastModified.get(sName))) {
                LOG.debug("reloading object " + sName);
                groovyRegistry.remove(sName);
            }
            T object = groovyRegistry.get(sName);
            if (object == null) {
                Class<?> clazz = compiler.compile(file);
                if (!Modifier.isAbstract(clazz.getModifiers())) {
                    object = groovyFactory.newInstance(clazz);
                    putObject(sName, object, file.lastModified());
                    return true;
                }
            }
        } catch (Exception e) {
            LOG.error("Error loading object! Continuing. file=" + file, e);
            return false;
        }

        return false;
    }

    private void putObject(String objectName, T object, long lastModified) {
        if (!groovyRegistry.isMutable()) {
            LOG.warn("Object registry is not mutable, discarding {}", objectName);
            return;
        }

        groovyRegistry.put(objectName, object);
        groovyClassLastModified.put(objectName, lastModified);
    }

    /**
     * Load and cache objectss by className
     *
     * @param classNames The class names to load
     * @return List of the loaded objects
     * @throws Exception If any specified object fails to load, this will abort. This is a safety mechanism so we can
     * prevent running in a partially loaded state.
     */
    @Override
    public List<T> putObjectsForClasses(String[] classNames) throws Exception {
        List<T> newObjects = new ArrayList<>();
        for (String className : classNames) {
            newObjects.add(putObjectForClassName(className));
        }
        return Collections.unmodifiableList(newObjects);
    }

    @Override
    public T putObjectForClassName(String className) throws Exception {
        Class<?> clazz = Class.forName(className);
        T object = groovyFactory.newInstance(clazz);
        putObject(className, object, System.currentTimeMillis());
        return object;

    }
    

}
