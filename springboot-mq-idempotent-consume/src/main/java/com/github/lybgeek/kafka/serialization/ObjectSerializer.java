package com.github.lybgeek.kafka.serialization;

import com.github.lybgeek.common.util.BeanUtils;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;


public class ObjectSerializer implements Serializer<Object> {


    @Override
    public byte[] serialize(String topic, Object object) {
        return BeanUtils.serialize(object);
    }

    @Override
    public void close() {
    }

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }
}
