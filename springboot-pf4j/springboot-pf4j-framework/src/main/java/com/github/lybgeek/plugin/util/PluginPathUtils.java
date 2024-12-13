package com.github.lybgeek.plugin.util;


import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;

import static org.pf4j.DefaultPluginManager.PLUGINS_DIR_CONFIG_PROPERTY_NAME;

@Slf4j
public final class PluginPathUtils {
    public final static String DEFAULT_PLUGIN_DIR = "pf4j-plugins";
    private final static String PLUGIN_DIR = System.getProperty(PLUGINS_DIR_CONFIG_PROPERTY_NAME, DEFAULT_PLUGIN_DIR);

    private PluginPathUtils(){

    }

    public static boolean isRemote(String pluginPath) {
        return pluginPath.startsWith("http://") || pluginPath.startsWith("https://");
    }


    public static Path getPluginPath(String pluginPath) {
        if (isRemote(pluginPath)) {
            Path file = getPathByUri(pluginPath);
            if (file != null) {
                return file;
            }
        }

        return new File(pluginPath).toPath();
    }

    private static Path getPathByUri(String pluginPathUri) {
        try {
            String pluginFileName = extractFileNameFromUrl(pluginPathUri);
            File localPluginPath = FileUtil.file(PLUGIN_DIR, pluginFileName);
            if (localPluginPath.exists()) {
                log.info("Using local plugin: {}", localPluginPath);
                return localPluginPath.toPath();
            }
            File file = downloadPlugin(pluginPathUri,localPluginPath);
            return file.toPath();
        } catch (IOException e) {
           log.error("Failed to download plugin from remote URL.",e);
        }
        return null;
    }




    public static File downloadPlugin(String remoteUrl, File pluginFile) throws IOException {
        if(!pluginFile.exists()){
            FileUtil.touch(pluginFile);
        }
        URL url = new URL(remoteUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (InputStream in = connection.getInputStream();
             FileOutputStream out = new FileOutputStream(pluginFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
        }

        log.info("Downloaded plugin to: {}", pluginFile);

        return pluginFile;
    }

    public static String extractFileNameFromUrl(String url) {
        int lastSlashIndex = url.lastIndexOf('/');
        if (lastSlashIndex == -1 || lastSlashIndex == url.length() - 1) {
            throw new IllegalArgumentException("Invalid URL: " + url);
        }
        return url.substring(lastSlashIndex + 1);
    }
}
