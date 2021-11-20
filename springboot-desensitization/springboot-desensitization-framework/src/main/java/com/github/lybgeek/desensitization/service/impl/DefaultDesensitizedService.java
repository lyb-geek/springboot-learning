package com.github.lybgeek.desensitization.service.impl;

import com.github.lybgeek.desensitization.enums.DesensitizedType;
import com.github.lybgeek.desensitization.exception.DesensitizedException;
import com.github.lybgeek.desensitization.property.DesensitizedProperties;
import com.github.lybgeek.desensitization.service.DesensitizedService;
import com.github.lybgeek.desensitization.util.DesensitizedUtils;
import com.github.lybgeek.desensitization.util.SensitiveWordUtils;
import org.springframework.util.ObjectUtils;

public class DefaultDesensitizedService implements DesensitizedService {

    private DesensitizedProperties properties;

    public DefaultDesensitizedService(DesensitizedProperties properties) {
        this.properties = properties;
    }

    public DefaultDesensitizedService() {
    }

    @Override
    public String desensitized(String str, DesensitizedType desensitizedType) {
        checkDesensitizedEnable();
        return DesensitizedUtils.desensitized(str,desensitizedType);
    }

    @Override
    public String desensitized(String str, String replaceChar) {
        checkDesensitizedEnable();
        return SensitiveWordUtils.replaceSensitiveWord(str,replaceChar);
    }

    private void checkDesensitizedEnable(){
        if(!ObjectUtils.isEmpty(properties) &&!properties.isEnabled()){
            throw new DesensitizedException("desensitized.enabled must be true");
        }
    }
}
