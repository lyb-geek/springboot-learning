package com.github.lybgeek.common.exception;

import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.github.lybgeek.common.model.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * 
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 运行期异常
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e) {
        String msg = e.getMessage();
        if(StringUtils.isEmpty(msg)){
            msg = "服务端异常";
        }
        log.error(msg, e);
//      Tracer.trace(e);
        return AjaxResult.error(msg,500);
    }


    @ExceptionHandler(BizException.class)
    public AjaxResult handleException(BizException e) {
        log.error("业务异常："+e.getMessage(), e);
        int code = ObjectUtils.isEmpty(e.getErrorCode()) ? 500 : e.getErrorCode();
//       Tracer.trace(e);
        return AjaxResult.error(e.getMessage(), code);
    }

    @ExceptionHandler(ParamFlowException.class)
    public AjaxResult handleException(ParamFlowException e) {
        log.error(e.getMessage(), e);
        return AjaxResult.error("Blocked by Sentinel ParamFlow Limit",429);
    }





}
