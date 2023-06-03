package com.github.lybgeek.refresh.test.monitor;

import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.Watcher;
import com.github.lybgeek.refresh.test.monitor.model.FileMonitor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.concurrent.TimeUnit;

;


@Slf4j
public final class FileMonitorExecutors {


    @SneakyThrows
    public static void monitorClassPathFileChange(FileMonitor fileMonitor, File file, ApplicationContext context){
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> configPath:" + file.getAbsolutePath());
       monitorPropertyChange(fileMonitor,file,context);
//        monitorFileChangeWithJdkWatchService(file,context);
    }


    @SneakyThrows
    private static void monitorPropertyChange(FileMonitor fileMonitor, File file,ApplicationContext context){
        if(fileMonitor.isFileScanEnabled()) {
            String ext = "." + FilenameUtils.getExtension(file.getName());
            String monitorDir = file.getParent();
            //轮询间隔时间
            long interval = TimeUnit.SECONDS.toMillis(fileMonitor.getFileScanInterval());
            //创建文件观察器
            FileAlterationObserver observer = new FileAlterationObserver(
                    monitorDir, FileFilterUtils.and(
                    FileFilterUtils.fileFileFilter(),
                    FileFilterUtils.suffixFileFilter(ext)));
//            FileAlterationObserver observer = new FileAlterationObserver(monitorDir);
            observer.addListener(new ConfigPropertyFileAlterationListener(context));

            //创建文件变化监听器
            FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
            //开始监听
            monitor.start();
        }
    }




    private static void monitorFileChangeWithJdkWatchService(File file,ApplicationContext context){
        WatchMonitor watchMonitor = WatchMonitor.create(file, WatchMonitor.ENTRY_MODIFY);
        watchMonitor.setWatcher(new Watcher(){
            @Override
            public void onCreate(WatchEvent<?> event, Path currentPath) {

            }

            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                log.info(">>>>>>>>>>>>>>>>>>>>>>>>> Monitor PropertyFile with path --> {}",currentPath);

            }

            @Override
            public void onDelete(WatchEvent<?> event, Path currentPath) {

            }

            @Override
            public void onOverflow(WatchEvent<?> event, Path currentPath) {

            }
        });

        //设置监听目录的最大深入，目录层级大于制定层级的变更将不被监听，默认只监听当前层级目录
//            watchMonitor.setMaxDepth(3);
        //启动监听
        watchMonitor.start();
    }



}
