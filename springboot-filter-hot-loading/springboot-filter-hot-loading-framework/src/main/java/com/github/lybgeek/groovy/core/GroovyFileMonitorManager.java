/*
 * Copyright 2018 Netflix, Inc.
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 */
package com.github.lybgeek.groovy.core;

import com.github.lybgeek.groovy.core.loader.GroovyLoader;
import com.github.lybgeek.groovy.core.monitor.GroovyFileAlterationListener;
import com.github.lybgeek.groovy.properties.GroovyProperties;
import com.github.lybgeek.groovy.util.DirectoryUtil;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


public class GroovyFileMonitorManager<T> {

    private static final Logger LOG = LoggerFactory.getLogger(GroovyFileMonitorManager.class);


    private final GroovyLoader<T> groovyLoader;
    private final GroovyProperties groovyProperties;

    public GroovyFileMonitorManager(GroovyProperties groovyProperties, GroovyLoader<T> groovyLoader) {
        this.groovyLoader = groovyLoader;
        this.groovyProperties = groovyProperties;
    }

    /**
     * Initialized the GroovyFileManager.
     *
     * @throws Exception
     */
    public void init() throws Exception {
        long startTime = System.currentTimeMillis();
        manageFiles();
        directoryChangeMonitor();
        LOG.info("Finished loading all classes. Duration = " + (System.currentTimeMillis() - startTime) + " ms.");
    }


    /**
     * Returns the directory File for a path. A Runtime Exception is thrown if the directory is in valid
     *
     * @param sPath
     * @return a File representing the directory path
     */
    public File getDirectory(String sPath) {
       return DirectoryUtil.getDirectory(sPath);
    }


    /**
     * Returns a List<File> of all Files from all polled directories
     *
     * @return
     */
    public List<File> getFiles() {
        List<File> list = new ArrayList<File>();
        if(groovyProperties.getDirectories() == null && groovyProperties.getDirectories().length == 0){
            return list;
        }
        for (String sDirectory : groovyProperties.getDirectories()) {
            if (sDirectory != null) {
                File directory = getDirectory(sDirectory);
                File[] aFiles = directory.listFiles(groovyProperties.getFilenameFilter());
                if (aFiles != null) {
                    list.addAll(Arrays.asList(aFiles));
                }
            }
        }
        return list;
    }

    @SneakyThrows
    void directoryChangeMonitor(){
          for (String sDirectory : groovyProperties.getDirectories()) {
            File directory = getDirectory(sDirectory);
            //创建文件观察器
            FileAlterationObserver observer = new FileAlterationObserver(
                    directory, FileFilterUtils.and(
                    FileFilterUtils.fileFileFilter(),
                    FileFilterUtils.suffixFileFilter(".groovy")));
            //轮询间隔时间
            long interval = TimeUnit.SECONDS.toSeconds(groovyProperties.getPollingIntervalSeconds());
            //创建文件观察器
            observer.addListener(new GroovyFileAlterationListener(this));
            //创建文件变化监听器
            FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
            //开始监听
            monitor.start();
        }
    }


    public void manageFiles() {
        List<File> aFiles = getFiles();
        for (File file : aFiles) {
            try {
                groovyLoader.putObject(file);
            }
            catch(Exception e) {
                LOG.error("Error init loading groovy files from disk by sync! file = " + file, e);
            }
        }

    }

    @Deprecated
    @SneakyThrows
    void fileChangeMonitor(File file){
        String ext = "." + FilenameUtils.getExtension(file.getName());
        String monitorDir = file.getParent();
        //轮询间隔时间
        long interval = TimeUnit.SECONDS.toMillis(groovyProperties.getPollingIntervalSeconds());
        //创建文件观察器
        FileAlterationObserver observer = new FileAlterationObserver(
                monitorDir, FileFilterUtils.and(
                FileFilterUtils.fileFileFilter(),
                FileFilterUtils.suffixFileFilter(ext)));
        observer.addListener(new GroovyFileAlterationListener(this));

        //创建文件变化监听器
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
        //开始监听
        monitor.start();
    }


    public GroovyLoader<T> getGroovyLoader() {
        return groovyLoader;
    }

    public GroovyProperties getGroovyProperties() {
        return groovyProperties;
    }
}
