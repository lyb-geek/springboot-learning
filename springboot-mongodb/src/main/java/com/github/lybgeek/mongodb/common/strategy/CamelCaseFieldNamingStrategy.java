package com.github.lybgeek.mongodb.common.strategy;

import java.util.Iterator;
import java.util.List;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.util.ParsingUtils;

//参考FieldNamingStrategy的其他实现
//userName->user_name
public class CamelCaseFieldNamingStrategy implements FieldNamingStrategy {
    @Override
    public String getFieldName(PersistentProperty<?> property) {
        List<String> parts = ParsingUtils.splitCamelCaseToLower(property.getName());
        StringBuilder sb = new StringBuilder();
        Iterator it = parts.iterator();
        if(it.hasNext()){
            sb.append(it.next().toString());
            while (it.hasNext()){
                sb.append("_");
                sb.append(it.next().toString());
            }
        }
        return sb.toString();
    }
}