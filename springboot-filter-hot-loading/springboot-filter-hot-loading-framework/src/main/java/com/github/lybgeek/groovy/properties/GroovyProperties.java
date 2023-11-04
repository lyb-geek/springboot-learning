package com.github.lybgeek.groovy.properties;


import com.github.lybgeek.groovy.core.GroovyFileFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.FilenameFilter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = GroovyProperties.PREFIX)
public class GroovyProperties {

    public final static String PREFIX = "lybgeek.groovy";

    private int fileProcessorThreads = 1;

    private int fileProcessorTasksTimeoutSecs = 1;

    /**
     * 需要加载的groovy的文件目录位置
     */
    private String[] directories = new String[]{"META-INF/groovydir"};


    /**
     * 扫描groovy文件目录间隔
     */
    private int pollingIntervalSeconds = 5;

    /**
     * 文件名字过滤器
     */
    private FilenameFilter filenameFilter = new GroovyFileFilter();

}