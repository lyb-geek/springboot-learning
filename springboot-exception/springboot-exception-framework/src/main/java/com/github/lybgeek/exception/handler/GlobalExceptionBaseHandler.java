package com.github.lybgeek.exception.handler;


import com.github.lybgeek.exception.BizException;
import com.github.lybgeek.exception.model.ValidMsg;
import com.github.lybgeek.exception.util.ExceptionUtil;
import com.github.lybgeek.resp.model.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionBaseHandler {

    // 运行期异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AjaxResult handleException(Exception e) {
        String msg = e.getMessage();
        if(StringUtils.isEmpty(msg)){
            msg = "服务端异常";
        }
        log.error(msg, e);
        return AjaxResult.error(msg, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }


    @ExceptionHandler(BizException.class)
    public AjaxResult handleException(BizException e)
    {
        return AjaxResult.error(e.getMessage(), e.getErrorCode());
    }


    /**
     *参数验证失败
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AjaxResult handleException(ConstraintViolationException e)
    {
        log.error("参数验证失败", e);
        return AjaxResult.error("参数验证失败", HttpStatus.BAD_REQUEST.value());
    }

    /**
     * 请求对象属性不满足校验规则
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AjaxResult handleException(MethodArgumentNotValidException e)
    {
        BindingResult result = e.getBindingResult();
        return getValidateExceptionAjaxResult(e, result);
    }

    /**
     * 请求对象属性不满足校验规则
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AjaxResult handleException(BindException e)
    {
        BindingResult result = e.getBindingResult();
        return getValidateExceptionAjaxResult(e, result);
    }

    /**
     * 参数校验异常
     * @param e
     * @param result
     * @return
     */
    private AjaxResult getValidateExceptionAjaxResult(Exception e, BindingResult result) {
        final List<FieldError> fieldErrors = result.getFieldErrors();
        List<ValidMsg> errorList = new ArrayList<>();
        for (FieldError error : fieldErrors) {
            errorList.add(new ValidMsg(null, error.getField(), error.getDefaultMessage()));
        }
        log.error("请求对象属性不满足校验规则", e);
        return AjaxResult.error("请求对象属性不满足校验规则", HttpStatus.BAD_REQUEST.value());
    }

    /**
     *参数类型数据异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AjaxResult handleException(MethodArgumentTypeMismatchException e)
    {
        List<ValidMsg> errorList = new ArrayList<>();
        errorList.add(new ValidMsg("", e.getName(), e.getMessage()));
        log.error("参数类型数据异常", e);
        return AjaxResult.error("参数类型数据异常", HttpStatus.BAD_REQUEST.value(),null,errorList);
    }
    /**
     *参数丢失异常
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AjaxResult handleException(MissingServletRequestParameterException e)
    {
        List<ValidMsg> errorList = new ArrayList<>();
        errorList.add(new ValidMsg("", e.getParameterName(), e.getMessage()));
        log.error("参数丢失异常", e);
        return AjaxResult.error("参数丢失异常",
                HttpStatus.BAD_REQUEST.value(),null,errorList);
    }

    /**
     * 消息不可读异常
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AjaxResult handleException(HttpMessageNotReadableException e)
    {
        String msg = ExceptionUtil.getExceptionMessage(e);
        log.error(msg, e);
        return AjaxResult.error(msg,
                HttpStatus.BAD_REQUEST.value());
    }


}
