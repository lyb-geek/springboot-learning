package com.github.lybgeek.watch.thread;

import com.github.lybgeek.model.JdbcConfig;
import com.github.lybgeek.util.SpringBeanUtil;
import com.github.lybgeek.watch.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

@Slf4j
public class WatcherThread implements Runnable {


	private WatchService watchService;

	public WatcherThread(WatchService watchService) {
		this.watchService = watchService;
	}

	@Override
	public void run() {
		pollingMonitor();

	}

	private void pollingMonitor() {
		while (true) {
			try {
				// 尝试获取监控池的变化，如果没有则一直等待
				WatchKey watchKey = watchService.take();
				for (WatchEvent<?> event : watchKey.pollEvents()) {
					if (Constants.CONFIG_NAME.equals(event.context().toString())) {
						log.info("监控到{}发生{}操作，将重新加载", event.context(), event.kind());
						String beanId = StringUtils.uncapitalize(JdbcConfig.class.getSimpleName());
						SpringBeanUtil.unregisterBean(beanId);
						SpringBeanUtil.registerBean(beanId, JdbcConfig.class.getName());

						break;
					}
				}
				// 完成一次监控就需要重置监控器一次
				watchKey.reset();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
