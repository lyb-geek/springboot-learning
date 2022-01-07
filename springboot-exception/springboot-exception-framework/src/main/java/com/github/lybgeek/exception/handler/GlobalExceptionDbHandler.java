package com.github.lybgeek.exception.handler;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.github.lybgeek.exception.util.ExceptionUtil;
import com.github.lybgeek.resp.model.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;


@RestControllerAdvice
@Slf4j
@ConditionalOnClass({SQLException.class, MybatisPlusException.class,
        MyBatisSystemException.class, org.apache.ibatis.exceptions.PersistenceException.class,
        BadSqlGrammarException.class, DuplicateKeyException.class})
public class GlobalExceptionDbHandler {




    /**
     * 数据库异常
     * @param e
     * @return
     */
    @ExceptionHandler({SQLException.class, MybatisPlusException.class,
            MyBatisSystemException.class, org.apache.ibatis.exceptions.PersistenceException.class,
            BadSqlGrammarException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AjaxResult dbException(Exception e) {
        String msg = ExceptionUtil.getExceptionMessage(e);
        log.error(msg, e);
        return AjaxResult.error(msg,HttpStatus.BAD_REQUEST.value());
    }

    /**
     * 数据库中已存在该记录
     * @param e
     * @return
     */
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public AjaxResult handleException(DuplicateKeyException e)
    {
        log.error("数据库中已存在该记录", e);
        return AjaxResult.error("数据库中已存在该记录", HttpStatus.CONFLICT.value());
    }


}
