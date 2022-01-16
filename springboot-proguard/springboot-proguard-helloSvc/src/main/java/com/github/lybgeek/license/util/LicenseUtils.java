package com.github.lybgeek.license.util;


import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.github.lybgeek.license.exception.LicenseException;
import org.springframework.util.StringUtils;

public final class LicenseUtils {

    private static SymmetricCrypto symmetricCrypto;

    private LicenseUtils(){

    }

    static {
        symmetricCrypto = initSymmetricCrypto();

    }


    public static String encrypt(String licensePlainCode){
        return symmetricCrypto.encryptHex(licensePlainCode);
    }

    public static String decrypt (String licenseCode){
       return symmetricCrypto.decryptStr(licenseCode, CharsetUtil.CHARSET_UTF_8);
    }

    public static void checkLicenseCode(String licenseCode){
        if(StringUtils.isEmpty(licenseCode)){
            throw new LicenseException("licenseCode can not be empty ");
        }
        String licensePlainCode = null;
        try {
            licensePlainCode = decrypt(licenseCode);
        } catch (Exception e) {
        }

        if(!"lybgeek".equals(licensePlainCode)){
            throw new LicenseException("licenseCode is invalid");
        }
    }

    private static SymmetricCrypto initSymmetricCrypto(){
        String secret = "JNHmVxubJIiM/HhS8hEEPA==";
        byte[] key = Base64.decode(secret.getBytes());
        return new SymmetricCrypto(SymmetricAlgorithm.AES, key);
    }

    private String generateSecretKey(){
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        return Base64.encode(key);
    }

    public static void main(String[] args) {
        String licensePlainCode = "lybgeek";
        String code = LicenseUtils.encrypt(licensePlainCode);
        System.out.println(code);
        System.out.println(LicenseUtils.decrypt(code));
    }
}
