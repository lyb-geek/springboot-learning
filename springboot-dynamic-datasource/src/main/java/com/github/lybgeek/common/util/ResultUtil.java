package com.github.lybgeek.common.util;

import com.github.lybgeek.common.model.Result;
import java.util.List;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public enum  ResultUtil {

  INSTANCE;


  public <T> Result<T> getFailResult(BindingResult bindingResult, Result<T> result) {

    result.setStatus(Result.fail);
    List<ObjectError> errorList = bindingResult.getAllErrors();
    StringBuilder messages = new StringBuilder();
    errorList.forEach(error -> {
      messages.append(error.getDefaultMessage()).append(";");
    });
    result.setMessage(messages.toString());
    return result;
  }
}
