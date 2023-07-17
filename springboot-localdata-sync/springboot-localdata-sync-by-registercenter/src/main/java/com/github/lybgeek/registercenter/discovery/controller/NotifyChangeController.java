package com.github.lybgeek.registercenter.discovery.controller;


import cn.hutool.json.JSONUtil;
import com.github.lybgeek.common.core.BaseDataSyncTrigger;
import com.github.lybgeek.common.model.SyncDataDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("nofity")
@RequiredArgsConstructor
@Slf4j
public class NotifyChangeController {

    private final BaseDataSyncTrigger registerCenterDataSyncTrigger;


    @PostMapping("change")
    public ResponseEntity<String> change(@RequestBody SyncDataDTO dataDTO){
             try {
                 log.info(">>>>>>>>>>>>>>>>>>>>>>>> Receive data ---> {}",dataDTO);
                 registerCenterDataSyncTrigger.callBack(dataDTO.getData());
                 return new ResponseEntity<>("success",HttpStatus.OK);
             } catch (Exception e) {
                log.error("change fail:" + e.getMessage(),e);
                 return new ResponseEntity<>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
             }

    }
}
