package com.github.lybgeek.parse.swagger.api.http.metadata.body.form;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FormDataBody {
    /**
     * form-data 请求体的键值对列表
     */
    private List<FormDataKV> formValues = new ArrayList<>();
}
