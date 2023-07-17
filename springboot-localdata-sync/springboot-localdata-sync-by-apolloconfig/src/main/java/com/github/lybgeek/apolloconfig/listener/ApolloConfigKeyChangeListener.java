package com.github.lybgeek.apolloconfig.listener;


import cn.hutool.json.JSONUtil;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.github.lybgeek.common.core.BaseDataSyncTrigger;
import com.github.lybgeek.common.model.SyncDataDTO;
import lombok.RequiredArgsConstructor;

import static com.github.lybgeek.apolloconfig.trigger.ApolloConfigDataSyncTrigger.SYNC_DATA;

@RequiredArgsConstructor
public class ApolloConfigKeyChangeListener implements ConfigChangeListener {

    private final BaseDataSyncTrigger baseDataSyncTrigger;

    @Override
    public void onChange(ConfigChangeEvent changeEvent) {
        for (String changedKey : changeEvent.changedKeys()) {
            if(SYNC_DATA.equals(changedKey)){
                ConfigChange change = changeEvent.getChange(changedKey);
                String newValueJson = change.getNewValue();
                if(JSONUtil.isJson(newValueJson)){
                    try {
                        SyncDataDTO syncDataDTO = JSONUtil.toBean(newValueJson,SyncDataDTO.class);
                        baseDataSyncTrigger.callBack(syncDataDTO.getData());
                    } catch (Exception e) {

                    }
                }

            }

        }
    }


}
