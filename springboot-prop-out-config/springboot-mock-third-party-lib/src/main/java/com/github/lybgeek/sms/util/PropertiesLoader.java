package com.github.lybgeek.sms.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * Properties加载工具类，提供多种加载Properties文件的方式，
 * 并支持将属性转换为常用数据类型。
 */
public final class PropertiesLoader {

    // 私有构造函数，防止实例化
    private PropertiesLoader() {}

    /**
     * 从类路径加载Properties文件
     * @param resourcePath 资源路径
     * @return 加载后的Properties对象
     * @throws IOException 如果加载过程中发生I/O错误
     */
    public static Properties loadFromClasspath(String resourcePath) throws IOException {
        Objects.requireNonNull(resourcePath, "Resource path cannot be null");
        URL resourceUrl = PropertiesLoader.class.getClassLoader().getResource(resourcePath);
        if (resourceUrl == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }
        try (InputStream inputStream = resourceUrl.openStream()) {
            return loadFromInputStream(inputStream);
        }
    }

    /**
     * 从输入流加载Properties
     * @param inputStream 输入流
     * @return 加载后的Properties对象
     * @throws IOException 如果加载过程中发生I/O错误
     */
    public static Properties loadFromInputStream(InputStream inputStream) throws IOException {
        Objects.requireNonNull(inputStream, "InputStream cannot be null");
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }

    /**
     * 从Reader加载Properties
     * @param reader Reader对象
     * @return 加载后的Properties对象
     * @throws IOException 如果加载过程中发生I/O错误
     */
    public static Properties loadFromReader(Reader reader) throws IOException {
        Objects.requireNonNull(reader, "Reader cannot be null");
        Properties properties = new Properties();
        properties.load(reader);
        return properties;
    }

    /**
     * 将Properties转换为Map
     * @param properties Properties对象
     * @return 转换后的Map
     */
    public static Map<String, String> toMap(Properties properties) {
        Objects.requireNonNull(properties, "Properties cannot be null");
        Map<String, String> result = new HashMap<>();
        for (String key : properties.stringPropertyNames()) {
            result.put(key, properties.getProperty(key));
        }
        return result;
    }

    /**
     * 获取Integer类型的属性值
     * @param properties Properties对象
     * @param key 属性键
     * @return Integer类型的属性值，如果不存在则返回null
     */
    public static Integer getInteger(Properties properties, String key) {
        String value = properties.getProperty(key);
        return value != null ? Integer.valueOf(value) : null;
    }

    /**
     * 获取Long类型的属性值
     * @param properties Properties对象
     * @param key 属性键
     * @return Long类型的属性值，如果不存在则返回null
     */
    public static Long getLong(Properties properties, String key) {
        String value = properties.getProperty(key);
        return value != null ? Long.valueOf(value) : null;
    }

    /**
     * 获取Boolean类型的属性值
     * @param properties Properties对象
     * @param key 属性键
     * @return Boolean类型的属性值，如果不存在则返回null
     */
    public static Boolean getBoolean(Properties properties, String key) {
        String value = properties.getProperty(key);
        return value != null ? Boolean.valueOf(value) : null;
    }
}    