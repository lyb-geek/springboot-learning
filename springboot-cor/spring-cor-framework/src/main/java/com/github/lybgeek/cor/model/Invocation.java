package com.github.lybgeek.cor.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Invocation {

    private Method method;

    private Object target;

    private Object[] args;


    public Object invoke() throws InvocationTargetException, IllegalAccessException {
        return method.invoke(target,args);
    }
}
