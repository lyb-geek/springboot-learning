package com.github.lybgeek.common.factory;

import com.github.lybgeek.common.classloader.CustomClassLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.lang.reflect.Field;

public class ServiceFactory {

    public static Object create(String secretKey,String serviceImplClzName, ApplicationContext applicationContext) throws Exception{
        CustomClassLoader customClassLoader = new CustomClassLoader(secretKey);
        Class clz = customClassLoader.loadClass(serviceImplClzName);
        return createBean(clz,applicationContext);
    }



    private static Object createBean(Class targetClz,ApplicationContext context) throws Exception{
        Object obj = targetClz.newInstance();
        Field[] fields = targetClz.getDeclaredFields();
        if(fields != null && fields.length > 0){
            for(Field field : fields){
                boolean hasAnnotation = field.isAnnotationPresent(Autowired.class);
                if(hasAnnotation){
                    setField(context,obj, field);
                }else{
                    hasAnnotation = field.isAnnotationPresent(Resource.class);
                    if(hasAnnotation){
                        setField(context,obj, field);
                    }
                }
            }
        }

        return obj;
    }

    public static  void setField(ApplicationContext context,Object obj, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(obj, context.getBean(field.getType()));
    }
}
