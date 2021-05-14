package com.github.lybgeek.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Hex;

/**
 * 利用hutool封装的加解密工具，以AES对称加密算法为例
 */
public final class EncryptorUtils {

    private static String secretKey;



    static {
        secretKey = Hex.encodeHexString(SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded());
        System.out.println("secretKey-->" + secretKey);
        System.out.println("--------------------------------------------------------------------------------------");
    }

    /**
     * 明文加密
     * @param plaintext
     * @return
     */
    @SneakyThrows
    public static String encode(String plaintext){
        System.out.println("明文字符串：" + plaintext);
        byte[] key = Hex.decodeHex(secretKey.toCharArray());
        String ciphertext =  SecureUtil.aes(key).encryptHex(plaintext);
        System.out.println("加密后字符串：" + ciphertext);

        return ciphertext;
    }

    /**
     * 解密
     * @param ciphertext
     * @return
     */
    @SneakyThrows
    public static String decode(String ciphertext){
        System.out.println("加密字符串：" + ciphertext);
        byte[] key = Hex.decodeHex(secretKey.toCharArray());
        String plaintext = SecureUtil.aes(key).decryptStr(ciphertext);
        System.out.println("解密后的字符串：" + plaintext);

        return plaintext;
    }

    /**
     * 明文加密
     * @param plaintext
     * @return
     */
    @SneakyThrows
    public static String encode(String secretKey,String plaintext){
        System.out.println("明文字符串：" + plaintext);
        byte[] key = Hex.decodeHex(secretKey.toCharArray());
        String ciphertext =  SecureUtil.aes(key).encryptHex(plaintext);
        System.out.println("加密后字符串：" + ciphertext);

        return ciphertext;
    }

    /**
     * 解密
     * @param ciphertext
     * @return
     */
    @SneakyThrows
    public static String decode(String secretKey,String ciphertext){
        System.out.println("加密字符串：" + ciphertext);
        byte[] key = Hex.decodeHex(secretKey.toCharArray());
        String plaintext = SecureUtil.aes(key).decryptStr(ciphertext);
        System.out.println("解密后的字符串：" + plaintext);

        return plaintext;
    }




    public static void main(String[] args) {

        String jdbcCiphertext = encode("jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai");
        decode(jdbcCiphertext);

        System.out.println("-----------------------------------------------------------------------------------------------------");

        String username = encode("root");
        decode(username);

        System.out.println("-----------------------------------------------------------------------------------------------------");

        String password = encode("123456");
        decode(password);
    }
}
