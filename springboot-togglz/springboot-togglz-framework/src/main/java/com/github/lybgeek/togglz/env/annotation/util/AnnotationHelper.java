package com.github.lybgeek.togglz.env.annotation.util;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public final class AnnotationHelper {
    private AnnotationHelper() {
    }


    public static  <A extends Annotation> A getAnnotation(Method method, Class<A> annotationType){
        A anno = AnnotatedElementUtils.getMergedAnnotation(method, annotationType);
        //从接口层面向上搜索
        if (anno == null){
            Class<?>[] ifaces = method.getDeclaringClass().getInterfaces();

            for (Class<?> ifaceClass : ifaces){
                Method ifaceMethod = ReflectionUtils.findMethod(ifaceClass, method.getName(), method.getParameterTypes());
                if (ifaceMethod != null) {
                    anno = getAnnotation(ifaceMethod, annotationType);
                    break;
                }
            }
        }

        //从父类逐级向上搜索
        if (anno == null){
            Class<?> superClazz = method.getDeclaringClass().getSuperclass();
            if (superClazz != null){
                Method superMethod = ReflectionUtils.findMethod(superClazz, method.getName(), method.getParameterTypes());
                if (superMethod != null){
                    return getAnnotation(superMethod, annotationType);
                }else{
                    return null;
                }
            }else{
                return null;
            }
        }

        return anno;
    }
}
