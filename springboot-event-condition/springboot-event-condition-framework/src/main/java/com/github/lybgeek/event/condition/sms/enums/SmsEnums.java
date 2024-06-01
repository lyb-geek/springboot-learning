package com.github.lybgeek.event.condition.sms.enums;


public enum SmsEnums {
    ALIYUN("aliyun"),
    TENCENT("tencent");

    private String name;

    SmsEnums(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
