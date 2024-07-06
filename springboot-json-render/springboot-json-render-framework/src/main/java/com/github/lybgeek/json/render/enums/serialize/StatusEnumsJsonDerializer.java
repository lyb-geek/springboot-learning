package com.github.lybgeek.json.render.enums.serialize;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.github.lybgeek.json.render.enums.StatusEnums;

import java.io.IOException;

public class StatusEnumsJsonDerializer extends JsonDeserializer<StatusEnums> {


    @Override
    public StatusEnums deserialize(JsonParser p, DeserializationContext ctx) throws IOException, JsonProcessingException {
         if(p.getText() != null){
             return StatusEnums.getByCode(Integer.valueOf(p.getText()));
         }
        return null;
    }
}
