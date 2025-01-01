package com.github.lybgeek.parse.swagger.api.http.metadata.body.form;

import com.github.lybgeek.parse.swagger.api.http.metadata.param.BodyParamType;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;


/**
 * @description:form-data 请求体的键值对
 **/
@Data
public class FormDataKV extends WWWFormKV {

    /**
     * 参数的文件列表
     * 当 paramType 为 FILE 时，参数值使用该字段
     * 其他类型使用 value字段
     */
    private List<FormFile> files;
    /**
     * 参数的 contentType
     */
    private String contentType;

    public boolean isFile() {
        return StringUtils.equalsIgnoreCase(getParamType(), BodyParamType.FILE.getValue());
    }
}
