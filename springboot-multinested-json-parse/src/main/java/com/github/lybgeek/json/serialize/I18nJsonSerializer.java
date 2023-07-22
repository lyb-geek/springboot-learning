package com.github.lybgeek.json.serialize;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import com.github.lybgeek.json.annotation.I18nField;
import com.github.lybgeek.json.service.I18nService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.io.IOException;


public class I18nJsonSerializer extends JsonSerializer<String> implements ContextualSerializer {

    @Autowired
    private I18nService i18nService;
    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(i18nService.getTargetContent(s));

    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {

        I18nField i18nField = beanProperty.getAnnotation(I18nField.class);

        if(!ObjectUtils.isEmpty(i18nField) && String.class.isAssignableFrom(beanProperty.getType().getRawClass())){
            return this;
        }
        return serializerProvider.findValueSerializer(beanProperty.getType(),beanProperty);
    }
}
