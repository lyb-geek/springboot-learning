/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.github.lybgeek.common.exception;

import com.github.lybgeek.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 异常处理器
 *
 *
 */
@ControllerAdvice
@Slf4j
public class BizExceptionHandler {

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(BizException.class)
	public Result handleBizException(BizException e){
	  Result result = Result.builder().status(Result.fail).message(e.getMessage()).build();
		return result;
	}



	@ExceptionHandler(Exception.class)
	public Result handleException(Exception e){
		log.error(e.getMessage(), e);
		Result result = Result.builder().status(Result.fail).message(e.getMessage()).build();
		return result;
	}
}
