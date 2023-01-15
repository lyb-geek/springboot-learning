package com.github.lybgeek.spring.parse;

import cn.hutool.core.io.FileUtil;
import org.springframework.context.index.CandidateComponentsIndexLoader;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @see CandidateComponentsIndexLoader
 */
public final class ComponentLoader {

    public static final String COMPONENTS_RESOURCE_LOCATION = "META-INF/lybgeek.components";

    private ComponentLoader(){}

    public static Set<String> load(ClassLoader classLoader) {

        try {
            Enumeration<URL> urls = classLoader.getResources(COMPONENTS_RESOURCE_LOCATION);
            if (!urls.hasMoreElements()) {
                return Collections.emptySet();
            }
            Set<String> componentClassNames = new LinkedHashSet<>();
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                List<String> contents = FileUtil.readUtf8Lines(url);
                for (String content : contents) {
                    if(content.startsWith("#")){
                        continue;
                    }
                    componentClassNames.add(content);
                }

            }
            return componentClassNames;

        }
        catch (IOException ex) {
            throw new IllegalStateException("Unable to load indexes from location [" +
                    COMPONENTS_RESOURCE_LOCATION + "]", ex);
        }

    }

    public static Set<String> load() {
        return load(Thread.currentThread().getContextClassLoader());
    }

}
