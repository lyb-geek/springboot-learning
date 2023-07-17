package com.github.lybgeek.apolloconfig.trigger;


import cn.hutool.json.JSONUtil;
import com.github.lybgeek.apolloconfig.service.ApolloConfigService;
import com.github.lybgeek.common.core.BaseDataSyncTrigger;
import com.github.lybgeek.common.event.DataSyncTriggerEvent;
import com.github.lybgeek.common.model.SyncDataDTO;
import com.github.lybgeek.common.property.DataSyncTriggerProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.event.EventListener;

@Slf4j
public class ApolloConfigDataSyncTrigger extends BaseDataSyncTrigger implements CommandLineRunner {

    public static final String SYNC_DATA = "syncData";

    private final ApolloConfigService apolloConfigService;

    public ApolloConfigDataSyncTrigger(ApolloConfigService apolloConfigService,DataSyncTriggerProperty dataSyncTriggerProperty) {
        super(dataSyncTriggerProperty);
        this.apolloConfigService = apolloConfigService;
    }

    @EventListener
    public void listener(DataSyncTriggerEvent dataSyncTriggerEvent){
        SyncDataDTO syncDataDTO = SyncDataDTO.builder()
                .data(dataSyncTriggerEvent.getSource())
                        .timeStamp(System.currentTimeMillis())
                                .build();
       apolloConfigService.publishAndListenerItem(SYNC_DATA, JSONUtil.toJsonStr(syncDataDTO));
    }


    @Override
    public void run(String... args) throws Exception {
        apolloConfigService.registerListener(SYNC_DATA);
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Register key : 【{}】 change listener",SYNC_DATA);
    }
}
