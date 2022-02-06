package com.github.lybgeek.gateway.dashboard.route.util;


import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class NumberUtils {

    private NumberUtils(){}

    private static Pattern pattern = Pattern.compile("[^0-9]");

    public static List<Integer> extractDigits(String str){
        List<Integer> digitList = new ArrayList<>();
        Matcher matcher = pattern.matcher(str);
        String result = matcher.replaceAll("");
        for(int i = 0; i < result.length(); i++){
            digitList.add(Integer.valueOf(StringUtils.substring(result, i, i + 1)));
        }

        return digitList;
    }


}








