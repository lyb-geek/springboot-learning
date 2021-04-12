package com.github.lybgeek.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.github.lybgeek.common.exception.BizException;
import com.github.lybgeek.common.model.AjaxResult;
import com.github.lybgeek.sentinel.fallback.SentinelRulesTestControllerFallBack;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: sentinel各种规则测试controller
 *
 **/
@RestController
@RequestMapping(value = "/test")
@Api(tags = "sentinel各种规则测试controller")
public class SentinelRulesTestController {

    private int count = 0;

    @GetMapping(value = "/flowRule/{msg}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="msg",defaultValue = "hello",value="信息", paramType = "path"),
    })
    @ApiOperation(value = "测试流控规则")
    public AjaxResult<String> testFlowRule(@PathVariable("msg") String msg){
        System.out.println(String.format("msg : %s",msg));
        return AjaxResult.success("测试流控规则");
    }

    @GetMapping(value = "/paramFlowRule/{msg}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="msg",defaultValue = "hello",value="信息", paramType = "path"),
    })
    @ApiOperation(value = "测试热点规则")
    @SentinelResource(value = "testParamFlowRule")
    public AjaxResult<String> testParamFlowRule(@PathVariable("msg") String msg) throws BlockException {
        System.out.println(String.format("msg : %s",msg));
        return AjaxResult.success("测试热点规则");
    }



    @GetMapping(value = "/degradeRule/{type}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type",defaultValue = "sumException",value="降级规则类型", paramType = "path"),
    })
    @ApiOperation(value = "测试降级规则")
    public AjaxResult<String> testDegradeRule(@PathVariable("type") String type){
        System.out.println(String.format("type : %s",type));
        //测试异常比例
        if("sumExceptionScale".equals(type)){
            count++;
            if(count % 2 == 0){
                System.out.println("异常比例达到50%");
                throw new BizException(500,"这是一个异常比例降级异常测试");
            }
            //测试异常数
        }else if("sumException".equals(type)){
            System.out.println("测试异常数");
            throw new BizException(500,"这是一个异常数降级异常测试");
        }

        return AjaxResult.success("测试降级规则");
    }

    @GetMapping(value = "/authorityRule/{msg}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="msg",defaultValue = "hello",value="信息", paramType = "path"),
            @ApiImplicitParam(name="origin",defaultValue = "pc",value="请求来源", paramType = "query")
    })
    @ApiOperation(value = "测试授权规则")
    public AjaxResult<String> testAuthorityRule(@PathVariable("msg") String msg,String origin){
        System.out.println(String.format("msg : %s,origin:%s",msg,origin));
        return AjaxResult.success("测试授权规则");
    }

    @GetMapping(value = "/fallback/{msg}")
    @SentinelResource(value = "testFallback",fallbackClass = SentinelRulesTestControllerFallBack.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name="msg",defaultValue = "hello",value="信息", paramType = "path"),
    })
    @ApiOperation(value = "测试熔断降级回调规则")
    public AjaxResult<String> testFallBack(@PathVariable("msg") String msg){
        System.out.println(String.format("msg : %s",msg));
        return AjaxResult.success("测试熔断降级回调规则");
    }







}
