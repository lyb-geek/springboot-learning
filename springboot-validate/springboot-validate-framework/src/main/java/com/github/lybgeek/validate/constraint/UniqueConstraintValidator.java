package com.github.lybgeek.validate.constraint;


import com.github.lybgeek.validate.constraint.anotation.Unique;
import com.github.lybgeek.validate.constraint.service.UniqueCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@Scope("prototype")
@Slf4j
public class UniqueConstraintValidator implements ConstraintValidator<Unique,Object>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private UniqueCheckService uniqueCheckService;

    private String checkField;

    @Override
    public void initialize(Unique constraintAnnotation) {
        uniqueCheckService = applicationContext.getBean(constraintAnnotation.checkUniqueBeanClass());
        checkField = constraintAnnotation.checkField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> uniqueCheckService:{},checkField:{},value:{}",uniqueCheckService,checkField,value);
        return !uniqueCheckService.checkUnique(value,checkField);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
