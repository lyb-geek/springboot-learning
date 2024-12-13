package com.github.lybgeek.plugin;


import com.github.lybgeek.plugin.util.PluginPathUtils;
import lombok.extern.slf4j.Slf4j;
import org.pf4j.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class PluginManagerWapper extends DefaultPluginManager{

    private final PluginManager pluginManager;


    public PluginManagerWapper(PluginManager pluginManager, String... pluginPath) {
        this.pluginManager = pluginManager;
        addPluginPath(pluginPath);
    }

    public PluginManagerWapper(String pluginPath) {
        this.pluginManager = new DefaultPluginManager();
        initPlugin(pluginPath);
    }


    public void addPluginPath(String... pluginPath){
        if(pluginPath!= null && pluginPath.length >= 1){
            for (String path : pluginPath) {
                initPlugin(path);
            }
        }
    }


    @Override
    public <T> List<T> getExtensions(Class<T> type){
        return pluginManager.getExtensions(type);
    }

    @Override
   public <T> List<T> getExtensions(Class<T> type, String pluginId){
        return pluginManager.getExtensions(type, pluginId);
   }

   @Override
   public PluginState stopPlugin(String pluginId){
        return pluginManager.stopPlugin(pluginId);
    }

    @Override
    public boolean disablePlugin(String pluginId){
        return pluginManager.disablePlugin(pluginId);
    }

    /**
     * Enables a plugin that has previously been disabled.
     *
     * @param pluginId the unique plugin identifier, specified in its metadata
     * @return true if plugin is enabled
     * @throws PluginRuntimeException if something goes wrong
     */
    @Override
    public boolean enablePlugin(String pluginId){
        return pluginManager.enablePlugin(pluginId);
    }

    /**
     * Deletes a plugin.
     *
     * @param pluginId the unique plugin identifier, specified in its metadata
     * @return true if the plugin was deleted
     * @throws PluginRuntimeException if something goes wrong
     */
    @Override
    public boolean deletePlugin(String pluginId){
        return pluginManager.deletePlugin(pluginId);
    }



    private void initPlugin(String pluginPath){
        try {
            Path path = PluginPathUtils.getPluginPath(pluginPath);
            if(!isLoaded(path)){
                pluginManager.loadPlugin(path);
            }
            pluginManager.startPlugins();
        } catch (Exception e) {
           log.error("load plugin error",e);
        }
    }

    public boolean isLoaded(Path pluginPath){
        PluginDescriptorFinder pluginDescriptorFinder = getPluginDescriptorFinder();
        PluginDescriptor pluginDescriptor = pluginDescriptorFinder.find(pluginPath);
        validatePluginDescriptor(pluginDescriptor);

        // Check there are no loaded plugins with the retrieved id
        String pluginId = pluginDescriptor.getPluginId();
        return pluginManager.getPlugin(pluginId) != null;

    }

    public PluginManager getPluginManager() {
        return pluginManager;
    }
}
