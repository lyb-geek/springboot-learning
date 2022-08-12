package com.github.lybgeek.interceptor;

import lombok.SneakyThrows;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;


public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private String body;

    @SneakyThrows
    public CustomHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        //获取请求body
        byte[] bodyBytes = StreamUtils.copyToByteArray(request.getInputStream());
        body = new String(bodyBytes, request.getCharacterEncoding());

    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        ServletInputStream servletInputStream = new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }
            @Override
            public boolean isReady() {
                return false;
            }
            @Override
            public void setReadListener(ReadListener readListener) {
            }
            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
        return servletInputStream;

    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
