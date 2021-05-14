package com.github.lybgeek.util;

import org.jasypt.properties.PropertyValueEncryptionUtils;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.util.StringUtils;


public final class JasyptEncryptorUtils {


    private static final String salt = "lybgeek";

    private static BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();

    static {
        basicTextEncryptor.setPassword(salt);
    }

    private JasyptEncryptorUtils(){}

    /**
     * 明文加密
     * @param plaintext
     * @return
     */
    public static String encode(String plaintext){
        System.out.println("明文字符串：" + plaintext);
        String ciphertext = basicTextEncryptor.encrypt(plaintext);
        System.out.println("加密后字符串：" + ciphertext);
        return ciphertext;
    }

    /**
     * 解密
     * @param ciphertext
     * @return
     */
    public static String decode(String ciphertext){
        System.out.println("加密字符串：" + ciphertext);
        ciphertext = "ENC(" + ciphertext + ")";
        if (PropertyValueEncryptionUtils.isEncryptedValue(ciphertext)){
            String plaintext = PropertyValueEncryptionUtils.decrypt(ciphertext,basicTextEncryptor);
            System.out.println("解密后的字符串：" + plaintext);
            return plaintext;
        }
        System.out.println("解密失败");
        return "";
    }

    /**
     * 解密
     * @param ciphertextWithEnc
     * @return
     */
    public static String decodeWithENC(String ciphertextWithEnc){
        System.out.println("加密字符串：" + ciphertextWithEnc);
        if (PropertyValueEncryptionUtils.isEncryptedValue(ciphertextWithEnc)){
            String plaintext = PropertyValueEncryptionUtils.decrypt(ciphertextWithEnc,basicTextEncryptor);
            System.out.println("解密后的字符串：" + plaintext);
            return plaintext;
        }
        System.out.println("解密失败");
        return "";
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
