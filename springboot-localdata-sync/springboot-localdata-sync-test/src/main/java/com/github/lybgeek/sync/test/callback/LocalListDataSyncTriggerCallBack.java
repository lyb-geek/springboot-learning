package com.github.lybgeek.sync.test.callback;


import com.github.lybgeek.common.core.DataSyncTriggerCallBack;
import com.github.lybgeek.sync.test.service.DataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LocalListDataSyncTriggerCallBack implements DataSyncTriggerCallBack {

    private final DataService dataService;

    @Override
    public void execute(Object data) {
        dataService.getDataList().add(data);
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Sync data:-->{}",data);
    }
}
