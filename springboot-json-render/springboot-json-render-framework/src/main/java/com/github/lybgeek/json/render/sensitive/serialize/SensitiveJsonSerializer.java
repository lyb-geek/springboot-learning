package com.github.lybgeek.json.render.sensitive.serialize;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.github.lybgeek.json.render.sensitive.annotation.Sensitive;
import com.github.lybgeek.json.render.sensitive.util.DesensitizedUtil;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class SensitiveJsonSerializer extends JsonSerializer<String> implements ContextualSerializer {

    private final DesensitizedUtil.DesensitizedType desensitizedType;

    public SensitiveJsonSerializer(){
        this.desensitizedType = DesensitizedUtil.DesensitizedType.CLEAR_TO_EMPTY;
    }



    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(DesensitizedUtil.desensitized(value,desensitizedType));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        Sensitive sensitive = property.getAnnotation(Sensitive.class);
        if (sensitive != null){
            return new SensitiveJsonSerializer(sensitive.type());
        }

        return prov.findValueSerializer(property.getType(), property);
    }
}
