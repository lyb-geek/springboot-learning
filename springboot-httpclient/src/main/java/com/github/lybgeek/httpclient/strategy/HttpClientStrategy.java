package com.github.lybgeek.httpclient.strategy;

import java.util.Map;

public interface HttpClientStrategy {

    String postForm(String url, Map<String,String> params, Map<String,String> headers);
}
