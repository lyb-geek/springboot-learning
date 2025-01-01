package com.github.lybgeek.parse.swagger.api.http.response;

import com.github.lybgeek.parse.swagger.api.http.metadata.body.form.FormFile;
import lombok.Data;

@Data
public class ResponseBinaryBody {
    private boolean sendAsBody;
    private String description;
    /**
     * 文件对象
     */
    private FormFile file;
}
