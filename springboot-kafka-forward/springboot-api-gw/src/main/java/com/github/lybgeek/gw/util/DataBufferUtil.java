package com.github.lybgeek.gw.util;

import lombok.experimental.UtilityClass;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;

import java.nio.charset.StandardCharsets;


@UtilityClass
public final class DataBufferUtil {

    public static String dataBufferToString(DataBuffer dataBuffer) {
        byte[] bytes = new byte[dataBuffer.readableByteCount()];
        dataBuffer.read(bytes);
        DataBufferUtils.release(dataBuffer); // 释放DataBuffer资源
        return new String(bytes, StandardCharsets.UTF_8);
    }

}