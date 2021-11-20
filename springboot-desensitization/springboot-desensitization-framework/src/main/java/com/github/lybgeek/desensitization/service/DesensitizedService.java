package com.github.lybgeek.desensitization.service;


import com.github.lybgeek.desensitization.enums.DesensitizedType;

public interface DesensitizedService {

     /**
     *  敏感词过滤
     * @param str 需要过滤的敏感词
     * @param desensitizedType 敏感词类型，{@link DesensitizedType}
     * @return 返回已经过滤的敏感词，敏感词以*替换
     */
     String desensitized(String str, DesensitizedType desensitizedType);

    /**
     *  敏感词过滤，需要使用到词库
     * @param str 需要过滤的敏感词
     * @param replaceChar 敏感词替换的支付
     * @return 返回已经过滤的敏感词，敏感词以replaceChar替换
     */
     String desensitized(String str, String replaceChar);
}
