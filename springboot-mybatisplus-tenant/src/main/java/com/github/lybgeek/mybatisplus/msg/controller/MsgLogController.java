package com.github.lybgeek.mybatisplus.msg.controller;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.lybgeek.mybatisplus.msg.entity.MsgLog;
import com.github.lybgeek.mybatisplus.msg.service.MsgLogService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lyb-geek
 * @since 2021-01-16
 */
@RestController
@RequestMapping("/msg/msglog")
@Api(value = "msgLog", description = "模块", tags = {"msgLog"})
public class MsgLogController {

    @Autowired
    private MsgLogService msgLogService;


    @ApiOperation(value = "添加msgLog", tags = {"msgLog"}, nickname = "addMsgLog")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,200:成功，否则失败")})
    @PostMapping(value="/add" ,produces = {"application/json"})
    public Boolean addMsgLog(@RequestBody MsgLog msgLog){
        Boolean isSuccess = msgLogService.save(msgLog);
        if(!isSuccess){
            throw new RuntimeException("msgLog add fail !");
        }
        return isSuccess;
    }

    @ApiOperation(value = "更新msgLog", tags = {"msgLog"}, nickname = "updateMsgLog")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,200:成功，否则失败")})
    @PutMapping(value="/update" ,produces = {"application/json"})
    public Boolean updateMsgLog(@RequestBody MsgLog msgLog){
        //Boolean isSuccess = msgLogService.updateById(msgLog);

        LambdaUpdateWrapper<MsgLog> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(MsgLog::getId, msgLog.getId());
        lambdaUpdateWrapper.set(MsgLog::getMsgContent,msgLog.getMsgContent());
        lambdaUpdateWrapper.set(MsgLog::getMsgRemark,msgLog.getMsgRemark());
        Boolean isSuccess = msgLogService.update(lambdaUpdateWrapper);
        if(!isSuccess){
            throw new RuntimeException("msgLog update fail !");
        }
        return isSuccess;
    }

    @ApiOperation(value = "查找msgLog", tags = {"msgLog"}, nickname = "getMsgLogById")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,200:成功，否则失败")})
    @ApiImplicitParams(
            {@ApiImplicitParam(paramType = "path", name = "id", value = "msgLog主键", required = true, dataTypeClass = Long.class)
            })
    @GetMapping(value="/{id}" ,produces = {"application/json"})
    public MsgLog getMsgLogById(@PathVariable("id")Long id){
        MsgLog msgLog = msgLogService.getById(id);
        if(ObjectUtils.isEmpty(msgLog)){
            throw new RuntimeException("msgLog not found!");
        }

        return msgLog;
    }

    @ApiOperation(value = "查找msgLog列表", tags = {"msgLog"}, nickname = "listMsgLogs")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,200:成功，否则失败")})
    @PostMapping(value="/list",produces = {"application/json"})
    public List<MsgLog> listmsgLogs(){
        List<MsgLog> msgLogs = msgLogService.list();
        return msgLogs;
    }


    @ApiOperation(value = "查找msgLog", tags = {"msgLog"}, nickname = "pageMsgLogs")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,200:成功，否则失败")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "curPage", value = "页数", defaultValue = "1", dataTypeClass = Long.class, paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "条数", defaultValue = "20", dataTypeClass = Long.class, paramType = "query", required = true),
    })
    @GetMapping(value="/page" ,produces = {"application/json"})
    public Page<MsgLog> pagemsgLogs(@RequestParam(value = "curPage") Long curPage,
                                      @RequestParam(value = "pageSize") Long pageSize){
        Page page = new Page(curPage,pageSize);
        Page<MsgLog> pageMsg = (Page<MsgLog>) msgLogService.page(page);

        return pageMsg;
    }

    @ApiOperation(value = "删除msgLog", tags = {"msgLog"}, nickname = "deleteMsgLogById")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,200:成功，否则失败")})
    @ApiImplicitParams(
            {@ApiImplicitParam(paramType = "path", name = "id", value = "msgLog主键", required = true, dataTypeClass = Long.class)
            })
    @DeleteMapping(value="/{id}" , produces = {"application/json"})
    public Boolean deleteMsgLogById(@PathVariable("id")Long id){
        boolean isSuccess = msgLogService.removeById(id);
        if(!isSuccess){
            throw new RuntimeException("msgLog delete fail !");
        }

        return isSuccess;
    }


}
