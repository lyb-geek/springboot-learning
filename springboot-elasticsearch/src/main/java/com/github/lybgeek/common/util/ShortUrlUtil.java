package com.github.lybgeek.common.util;

import java.util.Stack;

public class ShortUrlUtil {
    public static void main(String[] args) {
        long decimal = 1;
        System.out.println(decimal);
        System.out.println(encode(decimal));
        System.out.println(decode(encode(decimal)));
    }

    public static final char[] array={'q','w','e','r','t','y','u','i','o','p','a','s','d','f','g','h','j','k','l','z','x','c','v','b','n','m','0','1','2','3','4','5','6','7','8','9','Q','W','E','R','T','Y','U','I','O','P','A','S','D','F','G','H','J','K','L','Z','X','C','V','B','N','M'};

    /**
     * 10进制转62进制
     * @param number 短链接发号器下标索引
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
}