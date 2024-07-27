package com.github.lybgeek.validate.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AjaxResult<T> {
    public final static String SUCCESS_CODE = "200";
    public final static String SUCCESS_MESSAGE = "success";
    public final static String FAIL_CODE = "500";


    private String message;

    private String code;

    private T data;

    private boolean success;


    public static <T> AjaxResult<T> success(T data){
        AjaxResult<T> ajaxResult = new AjaxResult<>();
        ajaxResult.setSuccess(true);
        ajaxResult.setData(data);
        ajaxResult.setCode(SUCCESS_CODE);
        ajaxResult.setMessage(SUCCESS_MESSAGE);
        return ajaxResult;
    }

    public static <T> AjaxResult<T> fail(String message,String code,T data){
        AjaxResult<T> ajaxResult = new AjaxResult<>();
        ajaxResult.setSuccess(false);
        ajaxResult.setMessage(message);
        ajaxResult.setCode(code);
        ajaxResult.setData(data);
        return ajaxResult;
    }

    public static <T> AjaxResult<T> fail(String message,String code){
       return fail(message,code,null);
    }


}
