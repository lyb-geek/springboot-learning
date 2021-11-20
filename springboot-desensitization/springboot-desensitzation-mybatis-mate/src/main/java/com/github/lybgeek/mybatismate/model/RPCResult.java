package com.github.lybgeek.mybatismate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RPCResult<T>{
	private static final long serialVersionUID = 1L;

	public static final String MSG_SUCCESS = "操作成功";
	public static final String MSG_ERROR = "操作失败";
	public static final Integer CODE_SUCCESS = 0;

	private Boolean success;
	private String message = "";
	private Integer code;
	private T data;

	public static <T> RPCResult<T> success() {
		RPCResult<T> message = new RPCResult();
		message.setSuccess(true);
		message.setMessage(MSG_SUCCESS);
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
		message.setMessage(MSG_SUCCESS);
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
		message.setMessage(MSG_ERROR);
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
	public T check() throws RuntimeException {
		if (!Objects.isNull(this.getSuccess()) && this.getSuccess()) {
			return this.getData();
		} else {
			throw new RuntimeException(this.getMessage());
		}
	}
}
