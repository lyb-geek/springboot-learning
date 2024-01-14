

package com.github.lybgeek.spi;

import com.github.lybgeek.spi.factory.SpiFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Slf4j
public final class SpiLoader<T> {
    
    
    private static final String SPI_DIRECTORY = "META-INF/spi/";
    
    private static final Map<Class<?>, SpiLoader<?>> LOADERS = new ConcurrentHashMap<>();
    
    private final Class<T> clazz;
    
    private final ClassLoader classLoader;
    
    private final Holder<Map<String, ClassEntity>> cachedClasses = new Holder<>();
    
    private final Map<String, Holder<Object>> cachedInstances = new ConcurrentHashMap<>();
    
    private final Map<Class<?>, Object> targetInstances = new ConcurrentHashMap<>();
    

    
    /**
     * Instantiates a new Extension loader.
     *
     * @param clazz the clazz.
     */
    private SpiLoader(final Class<T> clazz, final ClassLoader cl) {
        this.clazz = clazz;
        this.classLoader = cl;
        if (!Objects.equals(clazz, SpiFactory.class)) {
            SpiLoader.getExtensionLoader(SpiFactory.class).getExtensionClassesEntity();
        }
    }
    
    /**
     * Gets extension loader.
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @param cl    the cl
     * @return the extension loader.
     */
    public static <T> SpiLoader<T> getExtensionLoader(final Class<T> clazz, final ClassLoader cl) {
        
        Objects.requireNonNull(clazz, "extension clazz is null");
        
        if (!clazz.isInterface()) {
            throw new IllegalArgumentException("extension clazz (" + clazz + ") is not interface!");
        }

        SpiLoader<T> extensionLoader = (SpiLoader<T>) LOADERS.get(clazz);
        if (Objects.nonNull(extensionLoader)) {
            return extensionLoader;
        }
        LOADERS.putIfAbsent(clazz, new SpiLoader<>(clazz, cl));
        return (SpiLoader<T>) LOADERS.get(clazz);
    }
    
    /**
     * Gets extension loader.
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @return the extension loader
     */
    public static <T> SpiLoader<T> getExtensionLoader(final Class<T> clazz) {
        return getExtensionLoader(clazz, SpiLoader.class.getClassLoader());
    }
    

    
    /**
     * Gets target.
     *
     * @param name the name
     * @return the target.
     */
    public T getTarget(final String name) {
        if (StringUtils.isBlank(name)) {
            throw new NullPointerException("get target name is null");
        }
        Holder<Object> objectHolder = cachedInstances.get(name);
        if (Objects.isNull(objectHolder)) {
            cachedInstances.putIfAbsent(name, new Holder<>());
            objectHolder = cachedInstances.get(name);
        }
        Object value = objectHolder.getValue();
        if (Objects.isNull(value)) {
            synchronized (cachedInstances) {
                value = objectHolder.getValue();
                if (Objects.isNull(value)) {
                    createExtension(name, objectHolder);
                    value = objectHolder.getValue();
                }
            }
        }
        return (T) value;
    }
    
    /**
     * get all target spi.
     *
     * @return list. target
     */
    public List<T> getTargets() {
        Map<String, ClassEntity> extensionClassesEntity = this.getExtensionClassesEntity();
        if (extensionClassesEntity.isEmpty()) {
            return Collections.emptyList();
        }
        if (Objects.equals(extensionClassesEntity.size(), cachedInstances.size())) {
            return (List<T>) this.cachedInstances.values().stream()
                    .map(Holder::getValue).collect(Collectors.toList());
        }
        List<T> targets = new ArrayList<>();
        List<ClassEntity> classEntities = new ArrayList<>(extensionClassesEntity.values());
        classEntities.forEach(v -> {
            T target = this.getTarget(v.getName());
            targets.add(target);
        });
        return targets;
    }
    
    @SuppressWarnings("unchecked")
    private void createExtension(final String name, final Holder<Object> holder) {
        ClassEntity classEntity = getExtensionClassesEntity().get(name);
        if (Objects.isNull(classEntity)) {
            throw new IllegalArgumentException(name + " name is error");
        }
        Class<?> aClass = classEntity.getClazz();
        Object o = targetInstances.get(aClass);
        if (Objects.isNull(o)) {
            try {
                targetInstances.putIfAbsent(aClass, aClass.newInstance());
                o = targetInstances.get(aClass);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new IllegalStateException("Extension instance(name: " + name + ", class: "
                        + aClass + ")  could not be instantiated: " + e.getMessage(), e);
                
            }
        }
        holder.setValue(o);
    }
    
    /**
     * Gets extension classes.
     *
     * @return the extension classes
     */
    public Map<String, Class<?>> getTargetClassesMap() {
        Map<String, ClassEntity> classes = this.getExtensionClassesEntity();
        return classes.values().stream().collect(Collectors.toMap(ClassEntity::getName, ClassEntity::getClazz, (a, b) -> a));
    }
    
    private Map<String, ClassEntity> getExtensionClassesEntity() {
        Map<String, ClassEntity> classes = cachedClasses.getValue();
        if (Objects.isNull(classes)) {
            synchronized (cachedClasses) {
                classes = cachedClasses.getValue();
                if (Objects.isNull(classes)) {
                    classes = loadExtensionClass();
                    cachedClasses.setValue(classes);
                }
            }
        }
        return classes;
    }
    
    private Map<String, ClassEntity> loadExtensionClass() {
        Map<String, ClassEntity> classes = new HashMap<>(16);
        loadDirectory(classes);
        return classes;
    }
    
    /**
     * Load files under SPI_DIRECTORY.
     */
    private void loadDirectory(final Map<String, ClassEntity> classes) {
        String fileName = SPI_DIRECTORY + clazz.getName();
        try {
            Enumeration<URL> urls = Objects.nonNull(this.classLoader) ? classLoader.getResources(fileName)
                    : ClassLoader.getSystemResources(fileName);
            if (Objects.nonNull(urls)) {
                while (urls.hasMoreElements()) {
                    URL url = urls.nextElement();
                    loadResources(classes, url);
                }
            }
        } catch (IOException t) {
            log.error("load extension class error {}", fileName, t);
        }
    }
    
    private void loadResources(final Map<String, ClassEntity> classes, final URL url) throws IOException {
        try (InputStream inputStream = url.openStream()) {
            Properties properties = new Properties();
            properties.load(inputStream);
            properties.forEach((k, v) -> {
                String name = (String) k;
                String classPath = (String) v;
                if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(classPath)) {
                    try {
                        loadClass(classes, name, classPath);
                    } catch (ClassNotFoundException e) {
                        throw new IllegalStateException("load extension resources error", e);
                    }
                }
            });
        } catch (IOException e) {
            throw new IllegalStateException("load extension resources error", e);
        }
    }
    
    private void loadClass(final Map<String, ClassEntity> classes,
                           final String name, final String classPath) throws ClassNotFoundException {
        Class<?> subClass = Objects.nonNull(this.classLoader) ? Class.forName(classPath, true, this.classLoader) : Class.forName(classPath);
        if (!clazz.isAssignableFrom(subClass)) {
            throw new IllegalStateException("load extension resources error," + subClass + " subtype is not of " + clazz);
        }

        ClassEntity oldClassEntity = classes.get(name);
        if (Objects.isNull(oldClassEntity)) {
            ClassEntity classEntity = new ClassEntity(name,  subClass);
            classes.put(name, classEntity);
        } else if (!Objects.equals(oldClassEntity.getClazz(), subClass)) {
            throw new IllegalStateException("load extension resources error,Duplicate class " + clazz.getName() + " name "
                    + name + " on " + oldClassEntity.getClazz().getName() + " or " + subClass.getName());
        }
    }
    
    /**
     * The type Holder.
     *
     * @param <T> the type parameter.
     */
    private static final class Holder<T> {
        
        private volatile T value;

        
        /**
         * Gets value.
         *
         * @return the value
         */
        public T getValue() {
            return value;
        }
        
        /**
         * Sets value.
         *
         * @param value the value
         */
        public void setValue(final T value) {
            this.value = value;
        }
    }
    
    private static final class ClassEntity {
        
        /**
         * name.
         */
        private final String name;
        

        /**
         * class.
         */
        private Class<?> clazz;
        
        private ClassEntity(final String name, final Class<?> clazz) {
            this.name = name;
            this.clazz = clazz;
        }
        
        /**
         * get class.
         *
         * @return class.
         */
        public Class<?> getClazz() {
            return clazz;
        }
        
        /**
         * set class.
         *
         * @param clazz class.
         */
        public void setClazz(final Class<?> clazz) {
            this.clazz = clazz;
        }
        
        /**
         * get name.
         *
         * @return name.
         */
        public String getName() {
            return name;
        }
        

    }
}
