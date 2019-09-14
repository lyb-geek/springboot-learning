package com.github.lybgeek.shorturl.util;

import com.github.lybgeek.shorturl.dto.ShortUrlHelperDTO;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;
import java.util.Stack;

public class ShortUrlUtil {
    public static void main(String[] args) {
//        long decimal = 100000;
//        System.out.println(decimal);
//        System.out.println(encode(decimal));
//        System.out.println(decode(encode(decimal)));

        ShortUrlHelperDTO shortUrlDTO = encode(100,3,62);
        System.out.println(shortUrlDTO);
        String radixStr = StringUtils.replace(shortUrlDTO.getShortUrl(),SHORT_URL_PREFIX,"");
        System.out.println(radixStr);
        long num = decode(radixStr,62,shortUrlDTO.getRandomStr());
        System.out.println(num);

    }

    public static final char[] array={'q','w','e','r','t','y','u','i','o','p','a','s','d','f','g','h','j','k','l','z','x','c','v','b','n','m','0','1','2','3','4','5','6','7','8','9','Q','W','E','R','T','Y','U','I','O','P','A','S','D','F','G','H','J','K','L','Z','X','C','V','B','N','M'};

    public static  final String SHORT_URL_PREFIX = "http://localhost:8080/";

    /**
     * 将10进制数字转为其他进制
     *
     * @param num    Long 型数字
     * @param length 转换后的字符串长度，
     * @param radix  要转换的进制
     * @return
     */
    public static ShortUrlHelperDTO encode(long num, int length, int radix) {
        ShortUrlHelperDTO shortUrlDTO = new ShortUrlHelperDTO();
        shortUrlDTO.setNumber(num);
        StringBuilder sb = new StringBuilder();
        int remainder = 0;
        while (num > radix - 1) {
            /**
             * 对 radix 进行求余，然后将余数追加至 sb 中，由于是从末位开始追加的，因此最后需要反转（reverse）字符串
             */
            remainder = Long.valueOf(num % radix).intValue();
            sb.append(array[remainder]);
            num = num / radix;
        }
        sb.append(array[Long.valueOf(num).intValue()]);
        String value = sb.reverse().toString();
        //不足指定长度的字符串，随机数向左填充
        if(value.length() < length){
           Integer fillLength = length - value.length();
           String randomStr = generateRandomStr(fillLength);
           value = randomStr + value;
           shortUrlDTO.setRandomStr(randomStr);
        }
        String shortUrl = SHORT_URL_PREFIX + value;
        shortUrlDTO.setShortUrl(shortUrl);
        return shortUrlDTO;
    }

    /**
     * 其他进制字符串转为数字
     *
     * @param str 编码后的被转换进制字符串
     * @param radix 被转换的进制
     * @param randomStr 随机字符串
     * @return 解码后的 10 进制数字
     */
    public static long decode(String str,int radix,String randomStr) {
        /**
         * 将随机字符串开头的字符串进行替换
         */
        if(StringUtils.isNotBlank(randomStr)){
            str = StringUtils.replace(str,randomStr,"");
        }

        long num = 0;
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            /**
             * 查找字符的索引位置
             */
            index = ArrayUtils.indexOf(array,str.charAt(i));
            /**
             * 索引位置代表字符的数值
             */
            num += (long) (index * (Math.pow(radix, str.length() - i - 1)));
        }

        return num;
    }

    /**
     * 10进制转62进制
     * @param number 短链接发号器索引
     * @return
     */
    public static String encode(long number){
            Long rest = number;
            Stack<Character> stack = new Stack<Character>();
            StringBuilder result = new StringBuilder(0);
            while(rest != 0){
                stack.add(array[new Long((rest - (rest / 62) * 62 )).intValue()]);
                rest = rest / 62;
            }
            while(!stack.isEmpty()){
                result.append(stack.pop());
            }
            return result.toString();

    }

    public static String getShortUrl(long number){
        String base62Value = encode(number);
        String shortUrl = SHORT_URL_PREFIX + base62Value;
        return shortUrl;
    }

    /**
     * 62进制转10进制
     * @param base62Str
     * @return
     */
    public static long decode(String base62Str){
        int multiple = 1;
        long result = 0;
        Character c;
        for(int i = 0;i < base62Str.length();i++){
            c = base62Str.charAt(base62Str.length() - i - 1);
            result += base62Value(c) * multiple;
            multiple = multiple * 62;
        }
        return result;
    }

    private static int base62Value(Character c){
        for(int i = 0;i < array.length;i++){
            if(c == array[i]){
                return i;
            }
        }
        return -1;
    }

    /**
     * 生成指定长度的随机数
     * @param length
     * @return
     */
    public static String generateRandomStr(int length) {
        String randomStr = "";
        Random random = new Random();
        for (int i = 0; i < length; i ++) {
            // 随机生成0或1，用来确定是当前使用数字还是字母 (0则输出数字，1则输出字母)
            int charOrNum = random.nextInt(2);
            if (charOrNum == 1) {
                // 随机生成0或1，用来判断是大写字母还是小写字母 (0则输出小写字母，1则输出大写字母)
                int temp = random.nextInt(2) == 1 ? 65 : 97;
                randomStr += (char) (random.nextInt(26) + temp);
            } else {
                // 生成随机数字
                randomStr += random.nextInt(10);
            }
        }
        return randomStr;
    }

}