package com.github.lybgeek.refresh.test.monitor.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.util.Properties;

/**
 * @description:
 * @author: Linyb
 * @date : 2023/5/11 11:21
 **/
@Slf4j
public class FileMonitor {
    public static final String FILE_SCAN_INTERVAL_KEY = "file.scan.interval";
    private static final String DEFAULT_FILE_SCAN_INTERVAL = "1" ;

    public static final String FILE_SCAN_ENABLED_KEY = "file.scan.enabled";
    private static final String DEFAULT_FILE_SCAN_ENABLED = "true" ;

    /**
     * 扫描周期,默认为1秒
     */
    private Integer fileScanInterval;

    /**
     * 是否开启文件扫描，默认为true
     */
    private boolean fileScanEnabled;


    private Properties properties;

    public FileMonitor(Properties properties) {
        this.properties = properties;
    }

    public FileMonitor() {
    }

    public Integer getFileScanInterval() {
        if(ObjectUtils.isEmpty(fileScanInterval)){
            if(!ObjectUtils.isEmpty(properties) &&!properties.isEmpty()){
                String value = properties.getProperty(FILE_SCAN_INTERVAL_KEY,DEFAULT_FILE_SCAN_INTERVAL);
                try {
                    return Integer.valueOf(value);
                } catch (NumberFormatException e) {
                    log.warn("illegal fileScanInterval value -->[{}],will set with default fileScanInterval value --> [{}]",value,DEFAULT_FILE_SCAN_INTERVAL);
                }
                return Integer.valueOf(DEFAULT_FILE_SCAN_INTERVAL);
            }
        }
        return fileScanInterval;
    }

    public void setFileScanInterval(Integer fileScanInterval) {
        this.fileScanInterval = fileScanInterval;
    }

    public boolean isFileScanEnabled() {
        if(ObjectUtils.isEmpty(fileScanInterval)){
            if(!ObjectUtils.isEmpty(properties) &&!properties.isEmpty()){
                String value = properties.getProperty(FILE_SCAN_ENABLED_KEY,DEFAULT_FILE_SCAN_ENABLED);
                return value.equalsIgnoreCase(DEFAULT_FILE_SCAN_ENABLED);
            }
        }
        return fileScanEnabled;
    }

    public void setFileScanEnabled(boolean fileScanEnabled) {
        this.fileScanEnabled = fileScanEnabled;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
