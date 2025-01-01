package com.github.lybgeek.swagger.test.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@ApiModel(
    value = "RPCResult",
    description = "rpc调用返回结果对象"
)
@JsonIgnoreProperties(
    ignoreUnknown = true
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RPCResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String MSG_SUCCESS = "操作成功";
    public static final String MSG_ERROR = "操作失败";
    public static final Integer CODE_SUCCESS = 0;
    @ApiModelProperty(
            value = "结果状态-true成功，false失败",
            name = "success"
    )
    private Boolean success;
    @ApiModelProperty(
            value = "返回提示信息",
            name = "message"
    )
    private String message = "";
    @ApiModelProperty(
            value = "返回业务编码",
            name = "code"
    )
    private Integer code;
    @ApiModelProperty(
            value = "返回的结果数据",
            name = "data"
    )
    private T data;
    @ApiModelProperty(
            value = "占位符参数，用于字段扩展",
            name = "args"
    )
    private Object args;

    public static <T> RPCResult<T> success() {
        RPCResult<T> message = new RPCResult();
        message.setSuccess(true);
        message.setMessage("操作成功");
        message.setCode(CODE_SUCCESS);
        return message;
    }

    public static <T> RPCResult<T> success(String messageText) {
        RPCResult<T> message = new RPCResult();
        message.setSuccess(true);
        message.setMessage(messageText);
        message.setCode(CODE_SUCCESS);
        return message;
    }

    public static <T> RPCResult<T> success(T data) {
        RPCResult<T> message = new RPCResult();
        message.setSuccess(true);
        message.setMessage("操作成功");
        message.setCode(CODE_SUCCESS);
        message.setData(data);
        return message;
    }

    public static <T> RPCResult<T> success(String messageText, T data) {
        RPCResult<T> message = new RPCResult();
        message.setSuccess(true);
        message.setMessage(messageText);
        message.setCode(CODE_SUCCESS);
        message.setData(data);
        return message;
    }

    public static <T> RPCResult<T> success(String messageText, Integer code) {
        RPCResult<T> message = new RPCResult();
        message.setSuccess(true);
        message.setMessage(messageText);
        message.setCode(code);
        return message;
    }

    public static <T> RPCResult<T> success(String messageText, Integer code, T data) {
        RPCResult<T> message = new RPCResult();
        message.setSuccess(true);
        message.setMessage(messageText);
        message.setCode(code);
        message.setData(data);
        return message;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static <T> RPCResult<T> error(String messageText) {
        RPCResult<T> message = new RPCResult();
        message.setSuccess(false);
        message.setMessage(messageText);
        return message;
    }

    public static <T> RPCResult<T> error(Integer code) {
        RPCResult<T> message = new RPCResult();
        message.setSuccess(false);
        message.setMessage("操作失败");
        message.setCode(code);
        return message;
    }

    public static <T> RPCResult<T> error(String messageText, Integer code) {
        RPCResult<T> message = new RPCResult();
        message.setSuccess(false);
        message.setMessage(messageText);
        message.setCode(code);
        return message;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static <T> RPCResult<T> error(String messageText, T data) {
        RPCResult<T> message = new RPCResult();
        message.setSuccess(false);
        message.setMessage(messageText);
        message.setData(data);
        return message;
    }

    public static <T> RPCResult<T> error(String messageText, Integer code, T data) {
        RPCResult<T> message = new RPCResult();
        message.setSuccess(false);
        message.setMessage(messageText);
        message.setCode(code);
        message.setData(data);
        return message;
    }

    public static <T> RPCResult<T> error(String messageText, Integer code, T data, Object args) {
        RPCResult<T> message = new RPCResult();
        message.setSuccess(false);
        message.setMessage(messageText);
        message.setCode(code);
        message.setData(data);
        message.setArgs(args);
        return message;
    }


}