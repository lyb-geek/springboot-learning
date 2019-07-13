package com.github.lybgeek.watch.thread;

import java.io.IOException;
import java.nio.file.WatchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WatcherHookThead implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(WatcherHookThead.class);

	private WatchService watchService;

	public WatcherHookThead(WatchService watchService) {
		super();
		this.watchService = watchService;
	}

	@Override
	public void run() {

		try {
			watchService.close();
			logger.info("watcher close success...");
		} catch (IOException e) {
			logger.error("watcher close fail:" + e.getMessage(), e);
		}
	}

}
