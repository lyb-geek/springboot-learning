package com.github.lybgeek.agent.properties;


import java.util.List;

public class ServiceLogProperties {

    public static String SKIP_LOG_SERVICE_NAMES_KEY = "agent.skipLogServiceNames";

    public static String SPITE = ",";

    public static String CLASS_METHOD_SPITE = "#";

    private List<String> skipLogServiceNameList;

    public ServiceLogProperties(){

    }

    public ServiceLogProperties(List<String> skipLogServiceNameList) {
        this.skipLogServiceNameList = skipLogServiceNameList;
    }

    public List<String> getSkipLogServiceNameList() {
        return skipLogServiceNameList;
    }

    public void setSkipLogServiceNameList(List<String> skipLogServiceNameList) {
        this.skipLogServiceNameList = skipLogServiceNameList;
    }

    @Override
    public String toString() {
        return "ServiceLogProperties{" +
                "skipLogServiceNameList=" + skipLogServiceNameList +
                '}';
    }
}
