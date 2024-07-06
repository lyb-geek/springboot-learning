package com.github.lybgeek.json.render.sensitive.annotation;


import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.lybgeek.json.render.sensitive.serialize.SensitiveJsonSerializer;
import com.github.lybgeek.json.render.sensitive.util.DesensitizedUtil;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveJsonSerializer.class)
public @interface Sensitive {

    DesensitizedUtil.DesensitizedType type();
}
