package com.github.lybgeek.registercenter.discovery.trigger;


import cn.hutool.core.collection.CollectionUtil;
import com.dtflys.forest.Forest;
import com.dtflys.forest.http.ForestResponse;
import com.github.lybgeek.common.core.BaseDataSyncTrigger;
import com.github.lybgeek.common.event.DataSyncTriggerEvent;
import com.github.lybgeek.common.model.SyncDataDTO;
import com.github.lybgeek.common.property.DataSyncTriggerProperty;
import com.github.lybgeek.registercenter.discovery.NamingService;
import com.github.lybgeek.registercenter.discovery.annotation.ApplicationName;
import com.github.lybgeek.registercenter.discovery.model.PeerUri;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Slf4j
public class RegisterCenterDataSyncTrigger extends BaseDataSyncTrigger  {

    private final NamingService namingService;

    @ApplicationName
    private String applicationName;


    public RegisterCenterDataSyncTrigger(NamingService namingService,DataSyncTriggerProperty dataSyncTriggerProperty) {
        super(dataSyncTriggerProperty);
        this.namingService = namingService;
    }



    @EventListener
    public void listener(DataSyncTriggerEvent dataSyncTriggerEvent){
        SyncDataDTO syncDataDTO = SyncDataDTO.builder()
                .data(dataSyncTriggerEvent.getSource())
                .timeStamp(System.currentTimeMillis())
                .build();
        nofityPeerNodes(syncDataDTO);

    }

    private void nofityPeerNodes(SyncDataDTO syncDataDTO) {
        List<PeerUri> peerUris = namingService.discover(applicationName, false);
        if(CollectionUtil.isNotEmpty(peerUris)){
            for (PeerUri uri : peerUris) {
                try {
                    String url = uri.getHttpUrl() + "/nofity/change";
                    ForestResponse forestResponse = Forest.post(url).contentTypeJson().addBody(syncDataDTO).executeAsResponse();
                    if(forestResponse.isSuccess() && forestResponse.status_2xx()){
                        String result = forestResponse.get(String.class);
                        log.info(">>>>>>>>>>>>>>>> Response:{}",result);

                    }
                } catch (Exception e) {
                   log.error(">>>>>>>>>>>>>>>>>> Nofity change fail,cause:"+ e.getMessage(),e);
                }

            }
        }
    }


}
