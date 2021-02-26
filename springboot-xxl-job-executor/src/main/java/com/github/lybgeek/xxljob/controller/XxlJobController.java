package com.github.lybgeek.xxljob.controller;


import com.alibaba.fastjson.JSON;
import com.github.lybgeek.xxljob.helper.XxljobClientHelper;
import com.github.lybgeek.xxljob.model.AjaxResult;
import com.xxl.job.core.biz.ExecutorBiz;
import com.xxl.job.core.biz.client.ExecutorBizClient;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.biz.model.TriggerParam;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: xxl-job restful方式调度
 *
 **/
@RestController
@RequestMapping(value = "xxl-job")
@Api(tags = "xxl-job restful调度")
@Profile("job")
@Slf4j
public class XxlJobController {

    @Autowired
    private XxljobClientHelper xxljobClientHelper;

    @ApiOperation(value = "手动触发任务")
    @GetMapping("/run")
    public AjaxResult execute(){
        String adminClientAddressUrl = xxljobClientHelper.getAdminClientAddressUrl();
        String accessToken = xxljobClientHelper.getAccessToken();
        log.info("adminClientAddressUrl:{},accessToken:{}", adminClientAddressUrl,accessToken);
        ExecutorBiz executorBiz = new ExecutorBizClient(adminClientAddressUrl, accessToken);
        ReturnT<String> retval = executorBiz.run(getTriggerParam());
        log.info("retval:{}", JSON.toJSONString(retval));
         // 200 表示正常、其他失败
        if(retval.getCode() == 200){
            return AjaxResult.success();
        }
        return AjaxResult.error(retval.getMsg(),retval.getCode());
    }

    private TriggerParam getTriggerParam(){
        TriggerParam triggerParam = new TriggerParam();
        // 任务ID
//        triggerParam.setJobId(15);
        // 任务标识
        triggerParam.setExecutorHandler("demoJobHandler");
        // 任务参数
        triggerParam.setExecutorParams("手动触发任务");
        // 任务阻塞策略，可选值参考 com.xxl.job.core.enums.ExecutorBlockStrategyEnum
        triggerParam.setExecutorBlockStrategy(ExecutorBlockStrategyEnum.COVER_EARLY.name());
        // 任务模式，可选值参考 com.xxl.job.core.glue.GlueTypeEnum
        triggerParam.setGlueType(GlueTypeEnum.BEAN.name());
        // GLUE脚本代码
        triggerParam.setGlueSource(null);
        // GLUE脚本更新时间，用于判定脚本是否变更以及是否需要刷新
        triggerParam.setGlueUpdatetime(System.currentTimeMillis());
        // 本次调度日志ID
        triggerParam.setLogId(triggerParam.getJobId());
        // 本次调度日志时间
        triggerParam.setLogDateTime(System.currentTimeMillis());
        return triggerParam;

    }
}
