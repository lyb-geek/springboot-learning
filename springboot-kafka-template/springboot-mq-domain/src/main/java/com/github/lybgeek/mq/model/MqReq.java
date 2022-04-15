package com.github.lybgeek.mq.model;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.lybgeek.mq.MqAccess;
import com.github.lybgeek.mq.exception.MqExecption;

import java.io.Serializable;



public class MqReq <T extends MqAccess> implements Serializable {

    private String topic;

    private T data;


    public MqReq(String topic, T data) {
        this.topic = topic;
        this.data = data;
    }

    public void validate(){
        if(StrUtil.isBlank(topic)){
            throw new MqExecption("topic can not be empty");
        }

        if(ObjectUtil.isEmpty(data)){
            throw new MqExecption("data can not be empty");
        }
    }

    public String getTopic() {
        validate();
        return topic;
    }

    public T getData() {
        validate();
        return data;
    }
}
