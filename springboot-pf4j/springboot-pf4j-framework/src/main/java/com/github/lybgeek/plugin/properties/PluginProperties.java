package com.github.lybgeek.plugin.properties;


import cn.hutool.core.io.FileUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

import static com.github.lybgeek.plugin.util.PluginPathUtils.DEFAULT_PLUGIN_DIR;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = PluginProperties.PRFEIX)
@Slf4j
public class PluginProperties {
    public static final String PRFEIX = "lybgeek.plugin";

    private String dir = DEFAULT_PLUGIN_DIR;



    public String getDir() {
        if(!FileUtil.exist(dir)){
            File newDir = FileUtil.mkdir(dir);
            log.warn("plugin dir:{} not exist,create it now!",newDir.getAbsolutePath());
        }
        return dir;
    }
}
