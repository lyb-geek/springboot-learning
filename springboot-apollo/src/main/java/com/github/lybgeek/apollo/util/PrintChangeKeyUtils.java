package com.github.lybgeek.apollo.util;

import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import org.springframework.util.CollectionUtils;

import java.util.Set;

public class PrintChangeKeyUtils {

    public static void printChange(ConfigChangeEvent changeEvent) {
        Set<String> changeKeys = changeEvent.changedKeys();
        if(!CollectionUtils.isEmpty(changeKeys)){
            for (String changeKey : changeKeys) {
                ConfigChange configChange = changeEvent.getChange(changeKey);
                System.out.println("key:"+changeKey+";oldValue:"+configChange.getOldValue()+";newValue:"+configChange.getNewValue());
            }
        }
    }
}
