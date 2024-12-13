package com.github.lybgeek.pf4j.model;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PluginConfig {

    public static final String PLUGIN_ID = "plugin.id";
    public static final String PLUGIN_DESCRIPTION = "plugin.description";
    public static final String PLUGIN_CLASS = "plugin.class";
    public static final String PLUGIN_VERSION = "plugin.version";
    public static final String PLUGIN_PROVIDER = "plugin.provider";
    public static final String PLUGIN_DEPENDENCIES = "plugin.dependencies";
    public static final String PLUGIN_REQUIRES = "plugin.requires";
    public static final String PLUGIN_LICENSE = "plugin.license";



    private String id;

    private String description;

    private String className;

    private String version;

    private String provider;

    private String dependencies;

    private String requires;

    private String license;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getDependencies() {
        return dependencies;
    }

    public void setDependencies(String dependencies) {
        this.dependencies = dependencies;
    }

    public String getRequires() {
        return requires;
    }

    public void setRequires(String requires) {
        this.requires = requires;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Map<String,String> convertToMap(){
    	Map<String,String> map = new HashMap<>();
    	map.put(PLUGIN_ID, id);
    	map.put(PLUGIN_DESCRIPTION, description);
    	map.put(PLUGIN_CLASS, className);
    	map.put(PLUGIN_VERSION, version);
    	map.put(PLUGIN_PROVIDER, provider);
    	map.put(PLUGIN_DEPENDENCIES, dependencies);
        map.put(PLUGIN_LICENSE, license);
    	map.put(PLUGIN_REQUIRES, requires);
    	return map;
    }
}
