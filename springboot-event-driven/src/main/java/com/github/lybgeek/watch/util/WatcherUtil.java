package com.github.lybgeek.watch.util;

import com.github.lybgeek.watch.thread.WatcherHookThead;
import com.github.lybgeek.watch.thread.WatcherThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.*;

@Slf4j
public enum  WatcherUtil {
	INSTANCE;
	private WatchService watchService;

	public void registerWatcher(String configName) throws IOException {
		log.info("{},registerWathcer....", configName);
		// 获取文件系统的WatchService对象
		watchService = FileSystems.getDefault().newWatchService();
		// 获取要监听的文件夹目录
		String filePath = new ClassPathResource(configName).getFile().getParent();
		Path path = Paths.get(filePath);
		// 注册要监监听指定目录的那些事件（创建、修改、删除）
		path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_CREATE);

	}

	public void executeWatcher() {
		Thread watcherThread = new Thread(new WatcherThread(watchService));
		watcherThread.setName("watcherThread");
		watcherThread.setDaemon(true);
		watcherThread.start();
	}

	public void shutDownWatcher() {
		Thread shutDownThread = new Thread(new WatcherHookThead(watchService));
		shutDownThread.setName("shutDownThread");
		Runtime.getRuntime().addShutdownHook(shutDownThread);
	}

	public void monitorConfig(String configName){
		try {
			registerWatcher(configName);
			executeWatcher();
			shutDownWatcher();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
