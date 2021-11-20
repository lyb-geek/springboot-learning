package com.github.lybgeek.desensitization.util;


import com.github.lybgeek.desensitization.annotation.Sensitive;
import com.github.lybgeek.desensitization.enums.DesensitizedType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;


import java.lang.reflect.Field;

public final class EntityUtils {

    private EntityUtils(){}

    public static void desensitized(Object entity){
        ReflectionUtils.doWithFields(entity.getClass(), field ->{
            ReflectionUtils.makeAccessible(field);
            Object value = field.get(entity);
            doDesensitized(entity, field, value);
        });
    }

    private static void doDesensitized(Object entity, Field field, Object value) throws IllegalAccessException {
        // 只有字符串类型才能脱敏,而且取值不能为null
        if(String.class.isAssignableFrom(field.getType())
                && field.isAnnotationPresent(Sensitive.class)
                && !ObjectUtils.isEmpty(value)){

            Sensitive sensitive = field.getAnnotation(Sensitive.class);
            String desensitizedValue = getDesensitizedValue(sensitive,value.toString());
            field.set(entity,desensitizedValue);

        }
    }

    public static String getDesensitizedValue(Sensitive sensitive,String orginalValue){

        DesensitizedType desensitizedType = sensitive.strategy();
        String desensitizedValue;
        //使用dfa算法过滤
        if(desensitizedType.equals(DesensitizedType.NONE) && sensitive.useDFA()){
            String replaceChar = StringUtils.repeat(sensitive.dfaReplaceChar(),sensitive.dfaReplaceCharRepeatCount());
            desensitizedValue = SensitiveWordUtils.replaceSensitiveWord(orginalValue,replaceChar);
        }else{
            desensitizedValue = DesensitizedUtils.desensitized(orginalValue,desensitizedType);
        }

        return desensitizedValue;

    }
}
