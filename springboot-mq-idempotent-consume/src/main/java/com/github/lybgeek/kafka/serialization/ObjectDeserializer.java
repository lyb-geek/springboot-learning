package com.github.lybgeek.kafka.serialization;

import com.github.lybgeek.common.util.BeanUtils;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;


public class ObjectDeserializer implements Deserializer<Object> {

    @Override
    public Object deserialize(String topic, byte[] bytes) {
        return BeanUtils.deserialize(bytes);
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }
}
