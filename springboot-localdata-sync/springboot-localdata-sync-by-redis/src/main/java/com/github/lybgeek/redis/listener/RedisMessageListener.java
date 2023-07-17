package com.github.lybgeek.redis.listener;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.github.lybgeek.common.core.BaseDataSyncTrigger;
import com.github.lybgeek.common.model.SyncDataDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

@RequiredArgsConstructor
@Slf4j
public class RedisMessageListener implements MessageListener{

    private final BaseDataSyncTrigger baseDataSyncTrigger;

    private final RedisTemplate redisTemplate;


    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] body = message.getBody();
        String dataJson = StrUtil.str(body, "utf-8");
        if(JSONUtil.isJson(dataJson)){
            try {
                SyncDataDTO dataDTO = (SyncDataDTO) redisTemplate.getHashValueSerializer().deserialize(body);
                baseDataSyncTrigger.callBack(dataDTO.getData());
            } catch (Exception e) {
                log.error(e.getMessage(),e);
            }
        }else{
            log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Data 【{}】 is not match json format ！！！",dataJson);
        }

    }
}
