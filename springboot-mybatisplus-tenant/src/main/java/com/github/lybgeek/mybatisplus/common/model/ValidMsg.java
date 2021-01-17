package com.github.lybgeek.mybatisplus.common.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ValidMsg implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String object;

    private String field;

    private String msg;

    public ValidMsg(String object, String field, String msg) {
        this.object = object;
        this.field = field;
        this.msg = msg;
    }

}
