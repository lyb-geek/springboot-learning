package com.github.lybgeek.common.enu;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期类型
 *
 *
 */
public enum DateType {
    /**
     * 年（yyyy）
     */
    YEAR("1", "yyyy"),
    /**
     * 年月（yyyy-MM）
     */
    MONTH("2", "yyyy-MM"),

    UP_MONTH_FOR_YEAR("12", "yyyy-MM"),
    /**
     * 年周（yyyy|ww）
     * 每年第一周的起始时间为当年第一天，每年最后一周的结束时间为当当年最后一天
     */
    YEAR_WEEK("3", "yyyy|ww"),

    UP_YEAR_WEEK_FOR_YEAR("13", "yyyy|ww"),
    /**
     * 年月周（yyyy-MM|W）
     * 每月第一周的起始时间为当月第一天，每月最后一周的结束时间为当月最后一天
     */
    MONTH_WEEK("4", "yyyy-MM|W"),

    UP_MONTH_WEEK_FOR_YEAR("14", "yyyy-MM|W"),
    /**
     * 年月日（yyyy-MM-dd）
     */
    DAY("5", "yyyy-MM-dd"),

    UP_DAY_FOR_YEAR("15", "yyyy-MM-dd");

    private String code;

    private String pattern;

    DateType(String code, String pattern) {
        this.code = code;
        this.pattern = pattern;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    public String getPattern() {
        return pattern;
    }

    public static DateType getInstance(String name) {
        return valueOf(name.toUpperCase());
    }

    private static Map<String, DateType> enumMap = new HashMap<>();

    static {
        DateType[] enums = values();
        for (DateType dateType : enums) {
            enumMap.put(dateType.getCode(), dateType);
        }
    }

    @JsonCreator
    public static DateType getInstanceByCode(String code) {
        return enumMap.get(code);
    }
}
