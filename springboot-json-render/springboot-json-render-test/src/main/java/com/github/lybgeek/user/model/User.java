package com.github.lybgeek.user.model;


import com.github.lybgeek.json.render.enums.StatusEnums;
import com.github.lybgeek.json.render.sensitive.annotation.Sensitive;
import com.github.lybgeek.json.render.sensitive.util.DesensitizedUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

//    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Sensitive(type = DesensitizedUtil.DesensitizedType.CHINESE_NAME)
    private String name;

    @Sensitive(type = DesensitizedUtil.DesensitizedType.PASSWORD)
    private String password;

//    @JsonSerialize(using = StatusEnumsJsonSerializer.class)
//    @JsonDeserialize(using = StatusEnumsJsonDerializer.class)
    private StatusEnums status;
}
