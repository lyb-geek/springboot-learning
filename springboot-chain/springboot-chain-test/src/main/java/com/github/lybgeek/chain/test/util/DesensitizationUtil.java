package com.github.lybgeek.chain.test.util;

import cn.hutool.core.util.StrUtil;

public class DesensitizationUtil {

    /**
     * 对名字进行脱敏处理，保留第一个字符，其余字符用星号代替
     * @param name 名字
     * @return 脱敏后的名字
     */
    public static String desensitizeName(String name) {
        if (name == null || name.length() <= 1) {
            return name;
        }
        return name.charAt(0) + StrUtil.repeat("*", name.length() - 1);
    }

    /**
     * 对手机号进行脱敏处理，保留前3位和后4位
     * @param mobile 手机号
     * @return 脱敏后的手机号
     */
    public static String desensitizeMobile(String mobile) {
        if (mobile == null || mobile.length() != 11) {
            return mobile;
        }
        return mobile.substring(0, 3) + "****" + mobile.substring(7);
    }

    /**
     * 对身份证号进行脱敏处理，保留前6位和后4位
     * @param idCard 身份证号
     * @return 脱敏后的身份证号
     */
    public static String desensitizeIdCard(String idCard) {
        if (idCard == null || (idCard.length() != 15 && idCard.length() != 18)) {
            return idCard;
        }
        return idCard.substring(0, 6) + "********" + idCard.substring(idCard.length() - 4);
    }

    /**
     * 对邮箱进行脱敏处理，保留@符号前后的首尾字符
     * @param email 邮箱
     * @return 脱敏后的邮箱
     */
    public static String desensitizeEmail(String email) {
        if (email == null) {
            return email;
        }
        int atIndex = email.indexOf('@');
        if (atIndex <= 1) {
            return email;
        }
        String prefix = email.substring(0, 1);
        String suffix = email.substring(atIndex - 1, atIndex);
        return prefix + "****" + suffix + email.substring(atIndex);
    }


    public static void main(String[] args) {
        System.out.println(desensitizeName("lybgeek"));
    }

}

