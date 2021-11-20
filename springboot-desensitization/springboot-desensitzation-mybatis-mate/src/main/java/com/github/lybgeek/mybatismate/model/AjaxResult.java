package com.github.lybgeek.mybatismate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AjaxResult<T>{
	private static final long serialVersionUID = 1L;
	public static final String MSG_SUCCESS = "操作成功";
	public static final String MSG_ERROR = "操作失败";
	public static final Integer CODE_SUCCESS = 0;

	private Boolean success;
	private String message = "";
	private Integer code;
	private T data;
	private Long nowDateTime;
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
