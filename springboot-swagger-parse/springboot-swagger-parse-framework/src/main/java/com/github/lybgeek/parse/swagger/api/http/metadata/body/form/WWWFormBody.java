package com.github.lybgeek.parse.swagger.api.http.metadata.body.form;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * x-www-form-urlencoded 请求体的键值对列表
 */
@Data
public class WWWFormBody {

    private List<WWWFormKV> formValues = new ArrayList<>();
}
