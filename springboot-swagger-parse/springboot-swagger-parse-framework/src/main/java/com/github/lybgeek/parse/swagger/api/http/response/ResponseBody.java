package com.github.lybgeek.parse.swagger.api.http.response;

import com.github.lybgeek.parse.swagger.api.http.metadata.body.json.JsonBody;
import com.github.lybgeek.parse.swagger.api.http.metadata.body.raw.RawBody;
import com.github.lybgeek.parse.swagger.api.http.metadata.body.xml.XmlBody;
import com.github.lybgeek.parse.swagger.api.http.request.RequestBody;
import lombok.Data;

import java.io.Serializable;

/**
 * 接口响应体、mock期望响应体中使用到的body
 */
@Data
public class ResponseBody implements Serializable {

    private static final long serialVersionUID = 1L;

    private String bodyType = RequestBody.BodyType.NONE.name();

    private JsonBody jsonBody = new JsonBody();

    private XmlBody xmlBody = new XmlBody();

    private RawBody rawBody = new RawBody();

    private ResponseBinaryBody binaryBody = new ResponseBinaryBody();

}
