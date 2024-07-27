package com.github.lybgeek.validate.constraint.service;


public interface UniqueCheckService {

    boolean checkUnique(Object value,String ...checkFields);
}
