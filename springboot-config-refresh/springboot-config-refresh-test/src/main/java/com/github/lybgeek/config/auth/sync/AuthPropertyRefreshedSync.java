package com.github.lybgeek.config.auth.sync;


import cn.hutool.core.collection.CollectionUtil;
import com.github.lybgeek.config.helper.RefreshPropertyHelper;
import com.github.lybgeek.config.property.RefreshConfigProperty;
import com.github.lybgeek.config.sync.PropertyRefreshedSync;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthPropertyRefreshedSync implements PropertyRefreshedSync {

    private final RefreshPropertyHelper refreshPropertyHelper;

    private final RefreshConfigProperty refreshConfigProperty;


    @Override
    public void execute(String name, Object value) {
        List<String> remoteEnvChangeUrls = refreshConfigProperty.getRemoteEnvChangeUrls();
        if(CollectionUtil.isNotEmpty(remoteEnvChangeUrls)){
            for (String remoteEnvChangeUrl : remoteEnvChangeUrls) {
                refreshPropertyHelper.refresh(name,value,remoteEnvChangeUrl);
            }
        }


    }
}
