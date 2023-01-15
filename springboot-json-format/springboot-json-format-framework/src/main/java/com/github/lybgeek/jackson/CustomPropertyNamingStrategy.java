package com.github.lybgeek.jackson;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import org.springframework.util.StringUtils;

import static com.github.lybgeek.constant.Constant.*;


public class CustomPropertyNamingStrategy extends PropertyNamingStrategy {


    @Override
    public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
        if (isSpecialPropertyName(defaultName)) {
            //将属性的get方法去除get，然后首字母转小写
            return StringUtils.uncapitalize(method.getName().substring(3));
        }
        return super.nameForGetterMethod(config,method,defaultName);
    }



    @Override
    public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
        if (isSpecialPropertyName(defaultName)) {
            //将属性的set方法去除set，然后首字母转小写
            return StringUtils.uncapitalize(method.getName().substring(3));
        }
        return super.nameForSetterMethod(config,method,defaultName);
    }

    private boolean isSpecialPropertyName(String defaultName) {
        if(defaultName.startsWith(DATE_PREFIX) || defaultName.startsWith(BIZ_PREFIX) || defaultName.startsWith(NUM_PREFIX)){
            return true;
        }
        return false;
    }


}
