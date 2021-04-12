package com.github.lybgeek.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ajaxResult",description = "返回结果对象")
public class AjaxResult<T>{
	private static final long serialVersionUID = 1L;
	public static final String MSG_SUCCESS = "操作成功";
	public static final String MSG_ERROR = "操作失败";
	public static final Integer CODE_SUCCESS = 0;

	@ApiModelProperty(value = "结果状态-true成功，false失败",name = "success")
	private Boolean success;
	@ApiModelProperty(value = "返回提示信息",name = "message")
	private String message = "";
	@ApiModelProperty(value = "返回业务编码",name = "code")
	private Integer code;
	@ApiModelProperty(value = "返回的结果数据",name = "data")
	private T data;
	@ApiModelProperty(value = "当前时间戳",name = "nowDateTime")
	private Long nowDateTime;
	@ApiModelProperty(value = "占位符参数，用于字段扩展",name = "args")
	private Object args;

	public static <T> AjaxResult<T> success() {
		AjaxResult<T> message = new AjaxResult();
		message.setNowDateTime(System.currentTimeMillis());
		message.setSuccess(true);
		message.setMessage(MSG_SUCCESS);
		message.setCode(CODE_SUCCESS);
		return message;
	}

	public static <T> AjaxResult<T> success(String messageText) {
		AjaxResult<T> message = new AjaxResult();
		message.setNowDateTime(System.currentTimeMillis());
		message.setSuccess(true);
		message.setMessage(messageText);
		message.setCode(CODE_SUCCESS);
		return message;
	}
	public static <T> AjaxResult<T> success(T data) {
		AjaxResult<T> message = new AjaxResult();
		message.setNowDateTime(System.currentTimeMillis());
		message.setSuccess(true);
		message.setMessage(MSG_SUCCESS);
		message.setCode(CODE_SUCCESS);
		message.setData(data);
		return message;
	}

	public static <T> AjaxResult<T> success(String messageText, T data) {
		AjaxResult<T> message = new AjaxResult();
		message.setNowDateTime(System.currentTimeMillis());
		message.setSuccess(true);
		message.setMessage(messageText);
		message.setCode(CODE_SUCCESS);
		message.setData(data);
		return message;
	}

	public static <T> AjaxResult<T> success(String messageText, Integer code) {
		AjaxResult<T> message = new AjaxResult();
		message.setNowDateTime(System.currentTimeMillis());
		message.setSuccess(true);
		message.setMessage(messageText);
		message.setCode(code);
		return message;
	}

	public static <T> AjaxResult<T> success(String messageText, Integer code, T data) {
		AjaxResult<T> message = new AjaxResult();
		message.setNowDateTime(System.currentTimeMillis());
		message.setSuccess(true);
		message.setMessage(messageText);
		message.setCode(code);
		message.setData(data);
		return message;
	}
	@Deprecated
	public static <T> AjaxResult<T> error(String messageText) {
		AjaxResult<T> message = new AjaxResult();
		message.setNowDateTime(System.currentTimeMillis());
		message.setSuccess(false);
		message.setMessage(messageText);
		return message;
	}
	public static <T> AjaxResult<T> error(Integer code) {
		AjaxResult<T> message = new AjaxResult();
		message.setNowDateTime(System.currentTimeMillis());
		message.setSuccess(false);
		message.setMessage(MSG_ERROR);
		message.setCode(code);
		return message;
	}

	public static <T> AjaxResult<T> error(String messageText, Integer code) {
		AjaxResult<T> message = new AjaxResult();
		message.setNowDateTime(System.currentTimeMillis());
		message.setSuccess(false);
		message.setMessage(messageText);
		message.setCode(code);
		return message;
	}
	@Deprecated
	public static <T> AjaxResult<T> error(String messageText, T data) {
		AjaxResult<T> message = new AjaxResult();
		message.setNowDateTime(System.currentTimeMillis());
		message.setSuccess(false);
		message.setMessage(messageText);
		message.setData(data);
		return message;
	}

	public static <T> AjaxResult<T> error(String messageText, Integer code, T data) {
		AjaxResult<T> message = new AjaxResult();
		message.setNowDateTime(System.currentTimeMillis());
		message.setSuccess(false);
		message.setMessage(messageText);
		message.setCode(code);
		message.setData(data);
		return message;
	}

	public static <T> AjaxResult<T> error(String messageText, Integer code, T data,Object args) {
		AjaxResult<T> message = new AjaxResult();
		message.setNowDateTime(System.currentTimeMillis());
		message.setSuccess(false);
		message.setMessage(messageText);
		message.setCode(code);
		message.setData(data);
		message.setArgs(args);
		return message;
	}
}
