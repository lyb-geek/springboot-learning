package com.github.lybgeek.groovy.core.monitor;

import com.github.lybgeek.groovy.core.GroovyFileMonitorManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;


@Slf4j
@RequiredArgsConstructor
public class GroovyFileAlterationListener extends FileAlterationListenerAdaptor {

    private final GroovyFileMonitorManager groovyFileMonitorManager;


    @Override
    public void onStart(FileAlterationObserver observer) {
        super.onStart(observer);
    }

    @Override
    public void onDirectoryCreate(File directory) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>> onDirectoryCreate with path --> {}",directory.getAbsolutePath());
        groovyFileMonitorManager.manageFiles();
    }

    @Override
    public void onDirectoryChange(File directory) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>> onDirectoryChange with path --> {}",directory.getAbsolutePath());
        groovyFileMonitorManager.manageFiles();
    }

    @Override
    public void onDirectoryDelete(File directory) {
        super.onDirectoryDelete(directory);
    }

    @Override
    public void onFileCreate(File file) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>> onFileCreate with path --> {}",file.getName());
        groovyFileMonitorManager.getGroovyLoader().putObject(file);
    }

    @Override
    public void onFileChange(File file) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>> onFileChange with path --> {}",file.getName());
        groovyFileMonitorManager.getGroovyLoader().putObject(file);
    }

    @Override
    public void onFileDelete(File file) {
        super.onFileDelete(file);

    }

    @Override
    public void onStop(FileAlterationObserver observer) {
        super.onStop(observer);
    }
}
