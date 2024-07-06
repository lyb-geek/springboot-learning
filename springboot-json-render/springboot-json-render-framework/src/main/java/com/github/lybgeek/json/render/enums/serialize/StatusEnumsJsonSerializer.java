package com.github.lybgeek.json.render.enums.serialize;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.lybgeek.json.render.enums.StatusEnums;

import java.io.IOException;

public class StatusEnumsJsonSerializer extends JsonSerializer<StatusEnums> {

    @Override
    public void serialize(StatusEnums value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getCode().toString());
    }
}
