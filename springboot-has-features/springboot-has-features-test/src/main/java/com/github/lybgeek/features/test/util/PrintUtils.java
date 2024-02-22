package com.github.lybgeek.features.test.util;


import java.util.Random;

public final class PrintUtils {

    private static Random random = new Random();

    private PrintUtils(){}


    public static String getFormatLogString(String content) {
       int color = random.nextInt(6) + 31;
       return getFormatLogString(content,color,0);
    }

    /**
     * @param color 颜色代号：背景颜色代号(41-46)；前景色代号(31-36)
     * @param type 样式代号：0无；1加粗；3斜体；4下划线
     * @param content 要打印的内容
     */
    private static String getFormatLogString(String content, int color, int type) {
        boolean hasType = type != 1 && type != 3 && type != 4;
        if (hasType) {
            return String.format("\033[%dm%s\033[0m", color, content);
        } else {
            return String.format("\033[%d;%dm%s\033[0m", color, type, content);
        }
    }

    public static void print(String content) {
        System.out.println(getFormatLogString(content));
    }



}
