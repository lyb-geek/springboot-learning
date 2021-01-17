package com.github.lybgeek.mybatisplus.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Result",description = "返回结果对象")
public class Result<T>{
	private static final long serialVersionUID = 1L;
	public static final String MSG_SUCCESS = "操作成功";
	public static final String MSG_ERROR = "操作失败";
	public static final Integer CODE_SUCCESS = 0;
	public static final Integer CODE_FAIL = 500;

	@ApiModelProperty(value = "结果状态-true成功，false失败",name = "success")
	private Boolean success;
	@ApiModelProperty(value = "返回提示信息",name = "message")
	private String message = "";
	@ApiModelProperty(value = "返回业务编码",name = "code")
	private Integer code;
	@ApiModelProperty(value = "返回的结果数据",name = "data")
	private T data;
	@ApiModelProperty(value = "占位符参数，用于字段扩展",name = "args")
	private Object args;

	public static <T> Result<T> success() {
		Result<T> message = new Result();
		message.setSuccess(true);
		message.setMessage(MSG_SUCCESS);
		message.setCode(CODE_SUCCESS);
		return message;
	}

	public static <T> Result<T> success(String messageText) {
		Result<T> message = new Result();
		message.setSuccess(true);
		message.setMessage(messageText);
		message.setCode(CODE_SUCCESS);
		return message;
	}
	public static <T> Result<T> success(T data) {
		Result<T> message = new Result();
		message.setSuccess(true);
		message.setMessage(MSG_SUCCESS);
		message.setCode(CODE_SUCCESS);
		message.setData(data);
		return message;
	}

	public static <T> Result<T> success(String messageText, T data) {
		Result<T> message = new Result();
		message.setSuccess(true);
		message.setMessage(messageText);
		message.setCode(CODE_SUCCESS);
		message.setData(data);
		return message;
	}

	public static <T> Result<T> success(String messageText, Integer code) {
		Result<T> message = new Result();
		message.setSuccess(true);
		message.setMessage(messageText);
		message.setCode(code);
		return message;
	}

	public static <T> Result<T> success(String messageText, Integer code, T data) {
		Result<T> message = new Result();
		message.setSuccess(true);
		message.setMessage(messageText);
		message.setCode(code);
		message.setData(data);
		return message;
	}
	public static <T> Result<T> error(Integer code) {
		Result<T> message = new Result();
		message.setSuccess(false);
		message.setMessage(MSG_ERROR);
		message.setCode(code);
		return message;
	}

	public static <T> Result<T> error(String messageText, Integer code) {
		Result<T> message = new Result();
		message.setSuccess(false);
		message.setMessage(messageText);
		message.setCode(code);
		return message;
	}
	@Deprecated
	public static <T> Result<T> error(String messageText, T data) {
		Result<T> message = new Result();
		message.setSuccess(false);
		message.setMessage(messageText);
		message.setData(data);
		return message;
	}

	public static <T> Result<T> error(String messageText, Integer code, T data) {
		Result<T> message = new Result();
		message.setSuccess(false);
		message.setMessage(messageText);
		message.setCode(code);
		message.setData(data);
		return message;
	}

	public static <T> Result<T> error(String messageText, Integer code, T data,Object args) {
		Result<T> message = new Result();
		message.setSuccess(false);
		message.setMessage(messageText);
		message.setCode(code);
		message.setData(data);
		message.setArgs(args);
		return message;
	}
}
