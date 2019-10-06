package com.github.lybgeek.common.exception;

public class BizException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String msg;
	private Integer code;

	public BizException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public BizException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}

	public BizException(String msg, Integer code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}

	public BizException(String msg, Integer code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

}
