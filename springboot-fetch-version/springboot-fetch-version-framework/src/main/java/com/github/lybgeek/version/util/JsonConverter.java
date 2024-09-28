package com.github.lybgeek.version.util;

import java.util.List;
import java.util.Map;

public class JsonConverter {


    public static String mapToJson(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (sb.length() > 1) {
                sb.append(", ");
            }
            sb.append("\"").append(entry.getKey()).append("\": ");
            convertValue(sb, entry.getValue());
        }

        sb.append("}");
        return sb.toString();
    }

    private static void convertValue(StringBuilder sb, Object value) {
        if (value instanceof Map) {
            sb.append(mapToJson((Map<String, Object>) value));
        } else if (value instanceof List) {
            sb.append(listToJson((List<?>) value));
        } else if (value instanceof String) {
            sb.append("\"").append(value).append("\"");
        } else {
            sb.append(value);
        }
    }

    private static String listToJson(List<?> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            convertValue(sb, list.get(i));
        }
        sb.append("]");
        return sb.toString();
    }
}