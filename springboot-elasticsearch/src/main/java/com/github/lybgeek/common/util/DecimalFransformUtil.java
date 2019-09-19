package com.github.lybgeek.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Stack;

public enum DecimalFransformUtil {

    INSTANCE;

    private static char[] array = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static String numStr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";


    /**
     * 十进制转其他进制
     * @param digital 十进制数字
     * @param radix 其他进制
     * @return
     */
    public String decimalCovertToOtherRadix(long digital,int radix){
        Long rest = digital;
        Stack<Character> stack = new Stack<Character>();
        StringBuilder result = new StringBuilder(0);
        while (rest != 0) {
            stack.add(array[new Long((rest % radix)).intValue()]);
            rest = rest / radix;
        }
        for (; !stack.isEmpty();) {
            result.append(stack.pop());
        }
        return result.length() == 0 ? "0":result.toString();

    }

    /**
     * 其他进制转为10进制
     * @param str 其他进制的值
     * @param radix 数字的进制
     * @return
     */
    public long otherRadixConvertToDecimal(String str,int radix){
        char ch[] = str.toCharArray();
        int len = ch.length;
        long result = 0;
        if (radix == 10) {
            return Long.parseLong(str);
        }
        long base = 1;
        for (int i = len - 1; i >= 0; i--) {
            int index = numStr.indexOf(ch[i]);
            result += index * base;
            base *= radix;
        }

        return result;

    }

    /**
     * 初始化其他进制数据，索引位置代表字符的数值
     */
    private static String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * 将10进制数字转为其他进制
     *
     * @param num    Long 型数字
     * @param length 转换后的字符串长度，
     * @param radix  要转换的进制
     * @return 要转换进制字符串
     */
    public static String covertDecimalToOtherRadix(long num, int length,int radix) {
        StringBuilder sb = new StringBuilder();
        int remainder = 0;

        while (num > radix - 1) {
            /**
             * 对 radix 进行求余，然后将余数追加至 sb 中，由于是从末位开始追加的，因此最后需要反转（reverse）字符串
             */
            remainder = Long.valueOf(num % radix).intValue();
            sb.append(chars.charAt(remainder));

            num = num / radix;
        }

        sb.append(chars.charAt(Long.valueOf(num).intValue()));
        String value = sb.reverse().toString();
        return StringUtils.leftPad(value, length, '0');
    }

    /**
     * 其他进制字符串转为数字
     *
     * @param str 编码后的被转换进制字符串
     * @param radix 被转换的进制
     * @return 解码后的 10 进制字符串
     */
    public static long convertOtherRadixToDecimal(String str,int radix) {
        /**
         * 将 0 开头的字符串进行替换
         */
        str = str.replace("^0*", "");
        long num = 0;
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            /**
             * 查找字符的索引位置
             */
            index = chars.indexOf(str.charAt(i));
            /**
             * 索引位置代表字符的数值
             */
            num += (long) (index * (Math.pow(radix, str.length() - i - 1)));
        }

        return num;
    }


    public static void main(String[] args) {
//        long digital = 10;
//        int radix = 62;
//        String otherRadix = DecimalFransformUtil.INSTANCE.decimalCovertToOtherRadix(digital,radix);
//        System.out.println(digital+"从十进制转换为"+radix+"进制的值是："+otherRadix);
//
//        long decimal = DecimalFransformUtil.INSTANCE.otherRadixConvertToDecimal(otherRadix,radix);
//
//        System.out.println(otherRadix+"从"+radix+"进制转换10进制的值是："+decimal);

        String a = DecimalFransformUtil.covertDecimalToOtherRadix(2,6,62);
        System.out.println(a);

        long i = DecimalFransformUtil.convertOtherRadixToDecimal(a,62);
        System.out.println(i);
    }

}