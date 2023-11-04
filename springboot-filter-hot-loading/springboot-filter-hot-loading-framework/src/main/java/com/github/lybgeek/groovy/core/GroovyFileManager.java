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
import com.github.lybgeek.groovy.properties.GroovyProperties;
import com.github.lybgeek.groovy.util.DirectoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class manages the directory polling for changes and new Groovy classes.
 * Polling interval and directories are specified in the initialization of the class, and a poller will check
 * for changes and additions.
 *
 */
@Deprecated
public class GroovyFileManager<T> {

    private static final Logger LOG = LoggerFactory.getLogger(GroovyFileManager.class);

    Thread poller;
    boolean bRunning = true;

    private final GroovyLoader<T> groovyLoader;
    private final ExecutorService processFilesService;
    private final GroovyProperties groovyProperties;

    public GroovyFileManager(GroovyProperties groovyProperties, GroovyLoader<T> groovyLoader) {
        this.groovyLoader = groovyLoader;
        this.groovyProperties = groovyProperties;
        ThreadFactory tf = new ThreadFactory() {
            AtomicInteger index = new AtomicInteger();
            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setDaemon(true);
                thread.setName("FilterFileManager_ProcessFiles-"+index.getAndIncrement());
                return null;
            }
        };
        this.processFilesService = Executors.newFixedThreadPool(groovyProperties.getFileProcessorThreads(), tf);
    }

    /**
     * Initialized the GroovyFileManager.
     *
     * @throws Exception
     */
    public void init() throws Exception
    {
        long startTime = System.currentTimeMillis();
        initManageFiles();
        manageFiles();
        startPoller();
        
        LOG.info("Finished loading all classes. Duration = " + (System.currentTimeMillis() - startTime) + " ms.");
    }

    /**
     * Shuts down the poller
     */
    public void shutdown() {
        stopPoller();
    }

    void stopPoller() {
        bRunning = false;
    }

    void startPoller() {
        poller = new Thread("GroovyFileManagerPoller") {
            {
                setDaemon(true);
            }

            @Override
            public void run() {
                while (bRunning) {
                    try {
                        sleep(groovyProperties.getPollingIntervalSeconds() * 1000);
                        manageFiles();
                    }
                    catch (Exception e) {
                        LOG.error("Error checking and/or loading groovy files from Poller thread.", e);
                    }
                }
            }
        };
        poller.start();
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
    List<File> getFiles() {
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

    /**
     * puts files into the GroovyLoader. The groovyLoader will only add new or changed classes
     *
     * @param aFiles a List<File>
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    void processGroovyFiles(List<File> aFiles) throws Exception {

        List<Callable<Boolean>> tasks = new ArrayList<>();
        for (File file : aFiles) {
            tasks.add(() -> {
                try {
                    return groovyLoader.putObject(file);
                }
                catch(Exception e) {
                    LOG.error("Error loading groovy files from disk! file = " + String.valueOf(file), e);
                    return false;
                }
            });
        }
        processFilesService.invokeAll(tasks, groovyProperties.getFileProcessorTasksTimeoutSecs(), TimeUnit.SECONDS);
    }

    void manageFiles()
    {
        try {
            List<File> aFiles = getFiles();
            processGroovyFiles(aFiles);
        }
        catch (Exception e) {
            String msg = "Error updating groovy filters from disk!";
            LOG.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }


    void initManageFiles() {
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



}
