package com.github.lybgeek.parse.swagger.api.http.metadata.body.binary;

import com.github.lybgeek.parse.swagger.api.http.metadata.body.form.FormFile;
import lombok.Data;

/**
 * @description:binary 请求体
 **/
@Data
public class BinaryBody {
    /**
     *  描述
     */
    private String description;
    /**
     * 文件对象
     */
    private FormFile file;
}
