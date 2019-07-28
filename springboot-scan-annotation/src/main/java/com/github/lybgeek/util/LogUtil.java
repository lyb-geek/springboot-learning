package com.github.lybgeek.util;

import com.alibaba.fastjson.JSON;
import com.github.lybgeek.model.OperateLog;
import com.github.lybgeek.service.OperateLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

@Slf4j
public enum  LogUtil {
    INSTACNE;

    public void saveLog(String method, Object[] params, Object result) {
        StopWatch stopWatch = new StopWatch(method+"日志记录耗时");
        stopWatch.start(method);
        String paramsJson = JSON.toJSONString(params);
        String resultJson = JSON.toJSONString(result);
        OperateLogService logService = SpringContextUtil.getBean(OperateLogService.class);
        OperateLog operateLog = OperateLog.builder().method(method).paramsJson(paramsJson).remark(method+"调用日志").resultJson(resultJson).build();
        logService.saveLog(operateLog);
        stopWatch.stop();
        log.info("{}",stopWatch.prettyPrint());
    }
}
