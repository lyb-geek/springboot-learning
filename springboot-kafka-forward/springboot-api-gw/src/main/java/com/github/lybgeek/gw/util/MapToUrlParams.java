package com.github.lybgeek.gw.util;

import java.net.URLEncoder;
import java.util.Map;
import java.util.stream.Collectors;

public final class MapToUrlParams {

    private MapToUrlParams() {

    }

    /**
     * 将Map转换为URL参数格式的字符串。
     *
     * @param params 需要转换的Map，键值对将被转换为key=value的形式。
     * @return URL编码后的参数字符串，键值对之间以'&'连接。
     */
    public static String mapToUrlParams(Map<String, String> params) {
        return params.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .map(entry -> {
                    try {
                        // 对键和值进行URL编码
                        String encodedKey = URLEncoder.encode(entry.getKey(), "utf-8");
                        String encodedValue = URLEncoder.encode(String.valueOf(entry.getValue()), "utf-8");
                        return encodedKey + "=" + encodedValue;
                    } catch (Exception e) {
                        throw new RuntimeException("Error encoding parameter", e);
                    }
                })
                .collect(Collectors.joining("&"));
    }

}
