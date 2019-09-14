package com.github.lybgeek.shorturl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShortUrlHelperDTO {
    /**
     * 发号器索引
     */
    private long number;

    /**
     * 随机字符串，主要用来补全
     */
    private String randomStr;

    /**
     * 生成的短链接
     */
    private String shortUrl;





}
