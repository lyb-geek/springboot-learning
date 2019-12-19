/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.github.lybgeek.validator;


import com.github.lybgeek.common.exception.BizException;
import com.github.lybgeek.validator.model.ValidResult;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * hibernate-validator校验工具类
 *
 * 参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 *
 *
 */
public class ValidatorUtils {

    private static Validator failFastValidator = Validation.byProvider(org.hibernate.validator.HibernateValidator.class)
        .configure()
        .failFast(true)
        .buildValidatorFactory().getValidator();

    /**
     * 全部校验
     */
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    private ValidatorUtils() {

    }

    public static Validator getValidator() {
        return validator;
    }

    public static Validator getFastValidator() {
        return failFastValidator;
    }

    /**
     * 校验对象
     * @param object        待校验对象
     * @param groups        待校验的组
     * @throws BizException  校验不通过，BizException
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws BizException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
        	ConstraintViolation<Object> constraint = (ConstraintViolation<Object>)constraintViolations.iterator().next();
            throw new BizException(constraint.getMessage());
        }
    }


    /**
     * 注解验证参数(快速失败模式)
     *
     * @param obj
     */
    public static <T> ValidResult fastFailValidate(T obj) {
        Set<ConstraintViolation<T>> constraintViolations = failFastValidator.validate(obj);
        //返回异常result
        if (constraintViolations.size() > 0) {
            return ValidResult.fail(constraintViolations.iterator().next().getPropertyPath().toString(), constraintViolations.iterator().next().getMessage());
        }
        return ValidResult.success();
    }

    /**
     * 注解验证参数(全部校验)
     *
     * @param obj
     */
    public static <T> ValidResult allCheckValidate(T obj,boolean isShowField) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
        //返回异常result
       return getAllCheckValidResult(isShowField,constraintViolations);
    }

    /**
     * 注解验证参数(快速失败模式)
     *
     * @param obj
     */
    public static <T> ValidResult fastFailValidate(T obj, Class<?>... groups) {
        Set<ConstraintViolation<T>> constraintViolations = failFastValidator.validate(obj,groups);
        //返回异常result
        if (constraintViolations.size() > 0) {
            return ValidResult.fail(constraintViolations.iterator().next().getPropertyPath().toString(), constraintViolations.iterator().next().getMessage());
        }
        return ValidResult.success();
    }

    /**
     * 注解验证参数(全部校验)
     *
     * @param obj
     */
    public static <T> ValidResult allCheckValidate(T obj,boolean isShowField, Class<?>... groups) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj,groups);
        return getValidResult(isShowField, constraintViolations);
    }

    private static <T> ValidResult getValidResult(boolean isShowField,
        Set<ConstraintViolation<T>> constraintViolations) {

        return getAllCheckValidResult(isShowField, constraintViolations);

    }

    private static <T> ValidResult getAllCheckValidResult(boolean isShowField,
        Set<ConstraintViolation<T>> constraintViolations) {

        //返回异常result
        if (constraintViolations.size() > 0) {
            List<String> errorMessages = new LinkedList<>();
            Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<T> violation = iterator.next();
                if(isShowField){
                    errorMessages.add(String.format("%s:%s", violation.getPropertyPath().toString(), violation.getMessage()));
                }else{
                    errorMessages.add(violation.getMessage());
                }

            }
            return ValidResult.fail(errorMessages);
        }
        return ValidResult.success();
    }
}
