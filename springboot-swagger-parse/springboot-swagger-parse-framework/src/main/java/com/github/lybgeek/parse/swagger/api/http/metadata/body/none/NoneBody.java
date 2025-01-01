package com.github.lybgeek.parse.swagger.api.http.metadata.body.none;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;



// 避免空的bean，序列化报错
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
@Data
public class NoneBody {
}
