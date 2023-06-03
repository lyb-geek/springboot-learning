package com.github.lybgeek.refresh.test.monitor.util;



import cn.hutool.core.io.FileUtil;
import com.github.lybgeek.refresh.test.monitor.FileMonitorExecutors;
import com.github.lybgeek.refresh.test.monitor.model.FileMonitor;
import org.springframework.context.ApplicationContext;

import java.io.File;

public final class ConfigFileUtil {

    private ConfigFileUtil(){}

    public final static String CONFIG_LOCATION_KEY = "configLocation";

    public static void setConfig() {
        System.out.println(getProjectPath());
        String configLocation = getProjectPath() + "/src/main/resources/config/config.yml";
        System.setProperty("spring.config.additional-location",configLocation);
        System.setProperty(CONFIG_LOCATION_KEY,configLocation);
    }

    public static void startFileMonitor(ApplicationContext context){
        FileMonitor fileMonitor = new FileMonitor();
        fileMonitor.setFileScanEnabled(true);
        fileMonitor.setFileScanInterval(1);
        FileMonitorExecutors.monitorClassPathFileChange(fileMonitor, FileUtil.file(System.getProperty(CONFIG_LOCATION_KEY)),context);
    }


    public static String getProjectPath() {
        String basePath = ConfigFileUtil.class.getResource("").getPath();
        return basePath.substring(0, basePath.indexOf("/target"));
    }


}
