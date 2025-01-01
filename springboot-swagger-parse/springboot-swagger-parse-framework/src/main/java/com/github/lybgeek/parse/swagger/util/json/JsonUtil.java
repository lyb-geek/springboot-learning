package com.github.lybgeek.parse.swagger.util.json;

import cn.hutool.core.map.MapUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.*;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.lybgeek.parse.swagger.constant.PropertyConstant;
import com.github.lybgeek.parse.swagger.util.json.node.Node;
import com.github.lybgeek.parse.swagger.util.json.node.support.OrdinaryNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class JsonUtil {
    private static final ObjectMapper objectMapper = JsonMapper.builder()
            .enable(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS)
            .build();
    private static final TypeFactory typeFactory = objectMapper.getTypeFactory();

    public static final int DEFAULT_MAX_STRING_LEN = Integer.MAX_VALUE;

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
        // 支持json字符中带注释符
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        // 自动检测所有类的全部属性
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        // 如果一个对象中没有任何的属性，那么在序列化的时候就会报错
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        // 设置JSON处理字符长度限制
//        objectMapper.getFactory()
//                .setStreamReadConstraints(StreamReadConstraints.builder().maxStringLength(DEFAULT_MAX_STRING_LEN).build());
        // 处理时间格式
        objectMapper.registerModule(new JavaTimeModule());

    }

    public static String toJSONString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toFormatJSONString(Object value) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] toJSONBytes(Object value) {
        try {
            return objectMapper.writeValueAsBytes(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object parseObject(String content) {
        return parseObject(content, Object.class);
    }

    public static <T> T parseObject(String content, Class<T> valueType) {
        try {
            return objectMapper.readValue(content, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseObject(String content, TypeReference<T> valueType) {
        try {
            return objectMapper.readValue(content, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseObject(InputStream src, Class<T> valueType) {
        try {
            return objectMapper.readValue(src, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List parseArray(String content) {
        return parseArray(content, Object.class);
    }

    public static <T> List<T> parseArray(String content, Class<T> valueType) {
        CollectionType javaType = typeFactory.constructCollectionType(List.class, valueType);
        try {
            return objectMapper.readValue(content, javaType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> parseArray(String content, TypeReference<T> valueType) {
        try {
            JavaType subType = typeFactory.constructType(valueType);
            CollectionType javaType = typeFactory.constructCollectionType(List.class, subType);
            return objectMapper.readValue(content, javaType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map parseMap(String jsonObject) {
        try {
            return (Map) objectMapper.readValue(jsonObject, new TypeReference() {
            });
        } catch (Exception e) {
//            throw new RuntimeException(e);
        }
        return null;
    }

    public static String jsonSchemaToJson(String jsonSchemaString, boolean isPreview) {
        // 解析 JSON Schema 字符串为 JsonNode
        JsonNode jsonSchemaNode = ApiJsonDataUtil.readTree(jsonSchemaString);
        Map<String, String> processMap = new HashMap<>();
        // 生成符合 JSON Schema 的 JSON
        JsonNode jsonNode = generateJson(jsonSchemaNode, processMap, isPreview);
        String jsonString = ApiJsonDataUtil.writerWithDefaultPrettyPrinter(jsonNode);
        if (MapUtil.isNotEmpty(processMap)) {
            for (String str : processMap.keySet()) {
                jsonString = jsonString.replace(str, processMap.get(str));
            }
        }
        return jsonString;
    }

    private static JsonNode generateJson(JsonNode jsonSchemaNode, Map<String, String> processMap, boolean isPreview) {
        if (jsonSchemaNode instanceof NullNode) {
            return NullNode.getInstance();
        }
        String type = getPropertyTextValue(jsonSchemaNode, PropertyConstant.TYPE);
        if (StringUtils.equals(type, PropertyConstant.OBJECT)) {
            JsonNode propertiesNode = jsonSchemaNode.get(PropertyConstant.PROPERTIES);
            // 遍历 properties
            if (propertiesNode != null) {
                ObjectNode jsonNode = ApiJsonDataUtil.createObjectNode();
                propertiesNode.fields().forEachRemaining(entry -> {
                    String propertyName = entry.getKey();
                    JsonNode propertyNode = entry.getValue();
                    // 根据属性类型生成对应的值
                    JsonNode valueNode = isPreview ? generateValueForPreview(entry.getKey(), propertyNode, processMap)
                            : generateValue(entry.getKey(), propertyNode, processMap);
                    // 将属性和值添加到 JSON 对象节点
                    jsonNode.set(propertyName, valueNode);
                });
                return jsonNode;
            }
        } else if (StringUtils.equals(type, PropertyConstant.ARRAY)) {
            JsonNode items = jsonSchemaNode.get(PropertyConstant.ITEMS);
            JsonNode maxItems = jsonSchemaNode.get(PropertyConstant.MAX_ITEMS);
            JsonNode minItems = jsonSchemaNode.get(PropertyConstant.MIN_ITEMS);
            if (items != null) {
                ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.instance);
                if (isPreview) {
                    items.forEach(item -> arrayNode.add(generateValueForPreview(null, item, processMap)));
                } else {
                    int max = isTextNotBlank(maxItems) ? maxItems.asInt() : Integer.MAX_VALUE;
                    int min = isTextNotBlank(minItems) ? minItems.asInt() : 0;
                    // 自动生成数据，根据 minItems 和 maxItems 生成
                    int itemSize = Math.min(items.size(), max);
                    for (int i = 0; i < itemSize; i++) {
                        JsonNode itemNode = items.get(i);
                        JsonNode valueNode = generateValue(null, itemNode, processMap);
                        arrayNode.add(valueNode);
                    }
                    if (min > itemSize) {
                        for (int i = itemSize; i < min; i++) {
                            // 如果不足最小个数，则默认补充字符类型的数组项
                            TextNode itemNode = new TextNode(generateStr(8));
                            arrayNode.add(itemNode);
                        }
                    }
                }
                return arrayNode;
            }
        }
        return null;
    }

    private static JsonNode generateValueForPreview(String propertyName, JsonNode propertyNode, Map<String, String> processMap) {
        // 获取属性类型
        if (propertyNode instanceof NullNode) {
            return NullNode.getInstance();
        }
        String type = getPropertyTextValue(propertyNode, PropertyConstant.TYPE);
        String value = getPropertyTextValue(propertyNode, PropertyConstant.EXAMPLE);
        JsonNode result;
        switch (type) {
            case PropertyConstant.STRING:
                result = new TextNode(StringUtils.isBlank(value) ? "string" : value);
                break;
            case PropertyConstant.INTEGER:
                if (isVariable(value)) {
                    result = getJsonNodes(propertyName, processMap, value);
                } else {
                    result = new IntNode(propertyNode.get(PropertyConstant.EXAMPLE).asInt());
                }
                break;
            case PropertyConstant.NUMBER:
                if (isVariable(value)) {
                    result = getJsonNodes(propertyName, processMap, value);
                } else {
                    try {
                        result = new DecimalNode(new BigDecimal(propertyNode.get(PropertyConstant.EXAMPLE).asText()));
                    } catch (Exception e) {
                        result = new DecimalNode(propertyNode.get(PropertyConstant.EXAMPLE).decimalValue());
                    }
                }
                break;
            case PropertyConstant.BOOLEAN:
                if (isVariable(value)) {
                    result = getJsonNodes(propertyName, processMap, value);
                } else {
                    result = BooleanNode.valueOf(propertyNode.get(PropertyConstant.EXAMPLE).asBoolean());
                }
                break;
            case PropertyConstant.OBJECT:
                result = generateJson(propertyNode, processMap, true);
                break;
            case PropertyConstant.ARRAY:
                result = generateJson(propertyNode, processMap, true);
                break;
            default:
                result = NullNode.getInstance();
                break;
        }
        return result;
    }

    private static JsonNode generateValue(String propertyName, JsonNode propertyNode, Map<String, String> processMap) {
        // 获取属性类型
        if (propertyNode instanceof NullNode) {
            return NullNode.getInstance();
        }
        String type = getPropertyTextValue(propertyNode, PropertyConstant.TYPE);
        String value = getPropertyTextValue(propertyNode, PropertyConstant.EXAMPLE);
        switch (type) {
            case PropertyConstant.STRING:
                return new TextNode(generateStrValue(propertyNode, value));
            case PropertyConstant.INTEGER:
                if (StringUtils.isBlank(value)) {
                    JsonNode enumValues = propertyNode.get(PropertyConstant.ENUM_VALUES);
                    JsonNode defaultValue = propertyNode.get(PropertyConstant.DEFAULT_VALUE);
                    if (enumValues != null && enumValues instanceof ArrayNode) {
                        value = enumValues.get(new SecureRandom().nextInt(enumValues.size())).asText();
                    } else if (isTextNotBlank(defaultValue)) {
                        value = defaultValue.asText();
                    } else {
                        JsonNode maximum = propertyNode.get(PropertyConstant.MAXIMUM);
                        JsonNode minimum = propertyNode.get(PropertyConstant.MINIMUM);
                        int max = isTextNotBlank(maximum) ? maximum.asInt() : Integer.MAX_VALUE;
                        int min = isTextNotBlank(minimum) ? minimum.asInt() : Integer.MIN_VALUE;
                        // 这里减去负数可能超过整型最大值，使用 Long 类型
                        long randomLong = new SecureRandom().nextLong() + min;
                        value = String.valueOf(randomLong);
                    }
                } else {
                    if (isVariable(value)) {
                        return getJsonNodes(propertyName, processMap, value);
                    }
                }
                try {
                    return new IntNode(Integer.valueOf(value));
                } catch (Exception e) {
                    return new IntNode(propertyNode.get(PropertyConstant.EXAMPLE).asInt());
                }
            case PropertyConstant.NUMBER:
                if (StringUtils.isBlank(value)) {
                    JsonNode enumValues = propertyNode.get(PropertyConstant.ENUM_VALUES);
                    JsonNode defaultValue = propertyNode.get(PropertyConstant.DEFAULT_VALUE);
                    if (enumValues != null && enumValues instanceof ArrayNode) {
                        value = enumValues.get(new SecureRandom().nextInt(enumValues.size())).asText();
                    } else if (isTextNotBlank(defaultValue)) {
                        value = defaultValue.asText();
                    } else {
                        JsonNode maximum = propertyNode.get(PropertyConstant.MAXIMUM);
                        JsonNode minimum = propertyNode.get(PropertyConstant.MINIMUM);
                        BigDecimal max = isTextNotBlank(maximum) ? new BigDecimal(maximum.asText()) : new BigDecimal(String.valueOf(Float.MAX_VALUE));
                        BigDecimal min = isTextNotBlank(minimum) ? new BigDecimal(minimum.asText()) : new BigDecimal(String.valueOf(Float.MIN_VALUE));
                        BigDecimal randomBigDecimal = min.add(new BigDecimal(String.valueOf(Math.random())).multiply(max.subtract(min)));
                        return new DecimalNode(randomBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP));
                    }
                } else {
                    if (isVariable(value)) {
                        return getJsonNodes(propertyName, processMap, value);
                    }
                }
                try {
                    return new DecimalNode(new BigDecimal(value));
                } catch (Exception e) {
                    return new DecimalNode(propertyNode.get(PropertyConstant.EXAMPLE).decimalValue());
                }
            case PropertyConstant.BOOLEAN:
                if (isVariable(value)) {
                    return getJsonNodes(propertyName, processMap, value);
                } else {
                    return BooleanNode.valueOf(propertyNode.get(PropertyConstant.EXAMPLE).asBoolean());
                }
            case PropertyConstant.OBJECT:
                return generateJson(propertyNode, processMap, false);
            case PropertyConstant.ARRAY:
                return generateJson(propertyNode, processMap, false);
            default:
                return NullNode.getInstance();
        }
    }

    private static String generateStrValue(JsonNode propertyNode, String value) {
        if (StringUtils.isBlank(value)) {
            JsonNode enumValues = propertyNode.get(PropertyConstant.ENUM_VALUES);
            JsonNode defaultValue = propertyNode.get(PropertyConstant.DEFAULT_VALUE);
            JsonNode pattern = propertyNode.get(PropertyConstant.PATTERN);
            JsonNode maxLength = propertyNode.get(PropertyConstant.MAX_LENGTH);
            JsonNode minLength = propertyNode.get(PropertyConstant.MIN_LENGTH);
            int max;
            int min;
            if (isTextNotBlank(maxLength) && isTextNotBlank(minLength)) {
                max = maxLength.asInt();
                min = minLength.asInt();
            } else if (isTextNotBlank(maxLength)) {
                // 只填了最大值，最小值默为 1
                max = maxLength.asInt();
                min = max > 0 ? 1 : 0;
            } else if (isTextNotBlank(minLength)) {
                // 只填了最小值，最大值默为最小值 + 10
                min = minLength.asInt();
                max = min + 10;
            } else {
                // 都没填，默认生成8位
                max = 8;
                min = 8;
            }
            if (enumValues != null && enumValues instanceof ArrayNode) {
                value = enumValues.get(new SecureRandom().nextInt(enumValues.size())).asText();
                if (isTextNotBlank(maxLength) && value.length() > max) {
                    value = value.substring(0, max);
                }
            } else if (isTextNotBlank(pattern)) {
                try {
                    Node node = new OrdinaryNode(pattern.asText());
                    value = node.random();
                } catch (Exception e) {
                    value = pattern.asText();
                }
            } else if (isTextNotBlank(defaultValue)) {
                value = defaultValue.asText();
                if (isTextNotBlank(maxLength) && value.length() > max) {
                    value = value.substring(0, max);
                }
                if (value.length() < min) {
                    value = value + generateStr(min - value.length());
                }
            } else {
                value = generateStr(new SecureRandom().nextInt(max - min + 1) + min);
            }
        }
        return value;
    }

    private static String generateStr(int length) {
//        return RandomStringGenerator.builder().withinRange('0', 'z').build().generate(length);
        return null;
    }

    private static boolean isTextNotBlank(JsonNode jsonNode) {
        return jsonNode != null && !(jsonNode instanceof NullNode) && StringUtils.isNotBlank(jsonNode.asText());
    }

    private static String getPropertyTextValue(JsonNode propertyNode, String key) {
        JsonNode jsonNode = propertyNode.get(key);
        return jsonNode == null || jsonNode instanceof NullNode ? StringUtils.EMPTY : jsonNode.asText();
    }

    private static boolean isVariable(String value) {
        return !StringUtils.equals(value, PropertyConstant.NULL) && (value.startsWith("@") || value.startsWith("${"));
    }

    private static TextNode getJsonNodes(String propertyName, Map<String, String> processMap, String value) {
        String key = StringUtils.join("\"", propertyName, "\"", " : \"", value, "\"");
        String targetValue = StringUtils.join("\"", propertyName, "\"", ": ", value);
        processMap.put(key, targetValue);
        return new TextNode(value);
    }

    public static String preview(String jsonSchema) {
        return jsonSchemaToJson(jsonSchema, true);
    }

    public static String jsonSchemaAutoGenerate(String jsonString) {
        return jsonSchemaToJson(jsonString, false);
    }

    public static ObjectNode parseObjectNode(String text) {
        try {
            return (ObjectNode) objectMapper.readTree(text);
        } catch (Exception e) {
            log.error("jsonSchemaAutoGenerate error", e);
        }
        return objectMapper.createObjectNode();
    }

    public static ObjectNode createObj() {
        return objectMapper.createObjectNode();
    }

    public static JSONArray parse(String text) {
        List<Object> list = parseObject(text, List.class);
        return new JSONArray(list);
    }


    public static JSONObject parseObj(String value) {
        try {
            if (StringUtils.isEmpty(value)) {
                throw new RuntimeException("value is null");
            }
            Map<String, Object> map = parseObject(value, Map.class);
            return new JSONObject(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
