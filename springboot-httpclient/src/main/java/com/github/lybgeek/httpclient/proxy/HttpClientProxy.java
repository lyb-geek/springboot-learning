package com.github.lybgeek.httpclient.proxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.lybgeek.common.model.Result;
import com.github.lybgeek.httpclient.annotation.*;
import com.github.lybgeek.httpclient.enu.HttpclientTypeEnum;
import com.github.lybgeek.httpclient.strategy.context.HttpClientContext;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HttpClientProxy implements MethodInterceptor {
    private static final String BRACKETS_PREFIX = "{";
    private static final String BRACKETS_SUBFIX = "}";

    private static final String JSON_OBJ_PREFIX = "{";
    private static final String JSON_ARRAY_PREFIX = "[{";

    private Class<?> targetClz;

    public  Object getInstance(Class<?> targetClz){
        this.targetClz = targetClz;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClz);
        enhancer.setCallback(this);
        return  enhancer.create();
    }


    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Remote remote = targetClz.getAnnotation(Remote.class);
        String url = remote.url();
        RemoteHeader[] remoteHeaders = remote.headers();
        Map<String,String> headers = new HashMap<>();
        if(ArrayUtils.isNotEmpty(remoteHeaders)){
            for (RemoteHeader remoteHeader : remoteHeaders) {
                if(StringUtils.isNotBlank(remoteHeader.name())){
                    headers.put(remoteHeader.name(),remoteHeader.value());
                }
            }
        }
        RemoteRequestMapping remoteRequestMapping = method.getAnnotation(RemoteRequestMapping.class);
        if(ObjectUtils.isNotEmpty(remoteRequestMapping)){
            String path = remoteRequestMapping.path();
            HttpclientTypeEnum type = remoteRequestMapping.type();
            Map<String,String> params = new HashMap<>();
            url = url + getParamPathAndSetReqParams(method, args, path,headers,params);

           String response = HttpClientContext.INSTANCE.getInstance(type).postForm(url,params,headers);

           return getResponse(method,response);

        }
       return null;

    }



    /**
     * 当url形如http;//www.abc/{id}替换为http://www.abc/1,1为具体的id值
     * @param method
     * @param args
     * @param path
     * @return
     */
    private String getParamPathAndSetReqParams(Method method, Object[] args, String path, Map<String,String> headers,Map<String,String> params) {
        if(ArrayUtils.isNotEmpty(args)){
            //参数注解，1维是参数，2维是注解
            Annotation[][] annotations = method.getParameterAnnotations();
            String[] paramNames = getParamterNames(method);
            for (int i = 0; i < annotations.length; i++) {
                Object param = args[i];
                String paramName = paramNames[i];
                Annotation[] paramAnn = annotations[i];
                boolean isBean = isBean(param);
                if (isBean) {
                    Map<String, String> beanParamsMap = objectToMap(param,new HashMap<>());
                    params.putAll(beanParamsMap);
                } else {
                    params.put(paramName, param.toString());
                }
                if (ArrayUtils.isNotEmpty(paramAnn)){
                    for (Annotation annotation : paramAnn) {
                        if (annotation.annotationType().equals(RemotePathParam.class)) {
                            RemotePathParam remotePathParam = (RemotePathParam) annotation;
                            String pathParam = BRACKETS_PREFIX + remotePathParam.value() + BRACKETS_SUBFIX;
                            path = StringUtils.replace(path, pathParam, param.toString());
                            if (params.containsKey(paramName)) {
                                params.remove(paramName);
                            }
                        }

                        if (annotation.annotationType().equals(RemoteReqHeader.class)) {
                            RemoteReqHeader remoteReqHeader = (RemoteReqHeader) annotation;
                            headers.put(remoteReqHeader.value(), param.toString());
                            if (params.containsKey(paramName)) {
                                params.remove(paramName);
                            }
                        }
                    }
            }
            }
        }
        return path;
    }

    /**
     * 获取方法参数名
     * @param method
     * @return
     */
    public  String[] getParamterNames(Method method) {
        DefaultParameterNameDiscoverer dpnd = new DefaultParameterNameDiscoverer();
        String[] params = dpnd.getParameterNames(method);
        return params;

    }


    private Object getResponse(Method method,String response){
        Result result = JSON.parseObject(response,Result.class);
        String jsonResult = JSON.toJSONString(result.getData());
        return this.getResult(method,jsonResult);
    }

    /**
     * 返回结果封装
     *
     * @param method
     * @param jsonResult
     * @return
     */
    private Object getResult(Method method, String jsonResult) {
        Object result = null;
        Class returnType = method.getReturnType();

        if (jsonResult.startsWith(JSON_OBJ_PREFIX)) {
             result = JSON.parseObject(jsonResult, returnType);
             covertJsonObject2Bean(method,result);
        } else if (jsonResult.startsWith(JSON_ARRAY_PREFIX)) {
            try {
                //获取具体集合的泛型类型
                 returnType = getMethodReturnType(method);
                 result = JSON.parseArray(jsonResult, returnType);
             //   result =  JSON.parseObject(jsonResult, new TypeReference<ArrayList<Object>>(){});
//                ObjectMapper objectMapper = new ObjectMapper();
//                result = objectMapper.readValue(jsonResult, returnType);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
               result = JSONObject.parseObject(jsonResult ,returnType);
            }
        } else {
            result = getNotJsonResult(method, jsonResult);
        }
        return result;
    }



    /**
     * 返回值不是JSON格式的参数封装
     *
     * @param method
     * @param jsonResult
     * @return
     */
    private Object getNotJsonResult(Method method, String jsonResult) {
        if (StringUtils.isBlank(jsonResult)) {
            return jsonResult;
        }

        Object returnType = method.getReturnType();
        String returnTypeStr = ((Class) returnType).getSimpleName();
        if (returnTypeStr.equalsIgnoreCase("Boolean")) {
            return Boolean.valueOf(jsonResult);
        } else if (returnTypeStr.equalsIgnoreCase("Integer") || returnTypeStr.equalsIgnoreCase("int")) {
            return Integer.valueOf(jsonResult);
        } else if (returnTypeStr.equalsIgnoreCase("Byte")) {
            return new Byte(jsonResult);
        } else if (returnTypeStr.equalsIgnoreCase("Short")) {
            return Short.valueOf(jsonResult);
        } else if (returnTypeStr.equalsIgnoreCase("Long")) {
            return Long.valueOf(jsonResult);
        } else if (returnTypeStr.equalsIgnoreCase("Float")) {
            return Float.valueOf(jsonResult);
        } else if (returnTypeStr.equalsIgnoreCase("Double")) {
            return Double.valueOf(jsonResult);
        } else if (returnTypeStr.equalsIgnoreCase("Boolean[]")) {
            String[] returnArr = covertString2Arrays(jsonResult);
            Boolean[] results = new Boolean[returnArr.length];
            for (int i = 0; i < returnArr.length; i++) {
                results[i] = Boolean.valueOf(returnArr[i]);
            }

            return results;

        } else if (returnTypeStr.equalsIgnoreCase("Integer[]") || returnTypeStr.equalsIgnoreCase("int[]")) {
            String[] returnArr = covertString2Arrays(jsonResult);
            Integer[] results = new Integer[returnArr.length];
            for (int i = 0; i < returnArr.length; i++) {
                results[i] = Integer.valueOf(returnArr[i]);
            }
            return results;
        } else if (returnTypeStr.equalsIgnoreCase("Byte[]")) {
            String[] returnArr = covertString2Arrays(jsonResult);
            Byte[] results = new Byte[returnArr.length];
            for (int i = 0; i < returnArr.length; i++) {
                results[i] = new Byte(returnArr[i]);
            }
            return results;
        } else if (returnTypeStr.equalsIgnoreCase("Short[]")) {
            String[] returnArr = covertString2Arrays(jsonResult);
            Short[] results = new Short[returnArr.length];
            for (int i = 0; i < returnArr.length; i++) {
                results[i] = Short.valueOf(returnArr[i]);
            }
            return results;
        } else if (returnTypeStr.equalsIgnoreCase("Long[]")) {
            String[] returnArr = covertString2Arrays(jsonResult);
            Long[] results = new Long[returnArr.length];
            for (int i = 0; i < returnArr.length; i++) {
                results[i] = Long.valueOf(returnArr[i]);
            }
            return results;
        } else if (returnTypeStr.equalsIgnoreCase("Float[]")) {
            String[] returnArr = covertString2Arrays(jsonResult);
            Float[] results = new Float[returnArr.length];
            for (int i = 0; i < returnArr.length; i++) {
                results[i] = Float.valueOf(returnArr[i]);
            }
            return results;
        } else if (returnTypeStr.equalsIgnoreCase("Double[]")) {
            String[] returnArr = covertString2Arrays(jsonResult);
            Double[] results = new Double[returnArr.length];
            for (int i = 0; i < returnArr.length; i++) {
                results[i] = Double.valueOf(returnArr[i]);
            }
            return results;
        } else if (returnTypeStr.equalsIgnoreCase("String[]")) {
            String[] returnArr = covertString2Arrays(jsonResult);

            return returnArr;
        }
        return jsonResult;
    }

    /**
     * 把返回值为字符串转数组
     *
     * @return
     */
    private  String[] covertString2Arrays(String value) {
        value = StringUtils.substring(value, StringUtils.indexOf(value, "[") + 1, StringUtils.lastIndexOf(value, "]"));
        return StringUtils.split(value, ",");
    }


    /**
     * 判断是否为基本型
     * @param obj
     * @return
     */
    private  boolean isPrimitive(Object obj) {
        try {
            return ((Class<?>)obj.getClass().getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 这个判断存在bug，但是现在简单用来判断是否为包装类
     * @param obj
     * @return
     */
    private  boolean isWapper(Object obj) {
        try {
            return obj instanceof Comparable;
        } catch (Exception e) {
            return false;
        }
    }

    private  boolean isCollectionOrIsMapOrIsArrays(Object object){
        boolean flag = object.getClass().isArray();
        if(!flag){
            flag = object instanceof Collection;
        }

        if(!flag){
            flag = object instanceof Map;
        }
        return flag;
    }


    private  boolean isBean(Object obj){
        boolean isBean = !(isCollectionOrIsMapOrIsArrays(obj) || isWapper(obj) || isPrimitive(obj));
        return isBean;
    }


    private  Map<String, String> objectToMap(Object obj, Map<String, String> map){
        Field[] fields = obj.getClass().getDeclaredFields();
        for(int i = 0, len = fields.length; i < len ; i++){
            String varName = fields[i].getName();
            try {
                fields[i].setAccessible(true);
                Object o = fields[i].get(obj);
                if(o != null){
                    if(this.isBean(o)){
                        objectToMap(o,map);
                    }else{
                        map.put(varName, o.toString());
                    }

                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
               log.error(e.getMessage(),e);
            }
        }
        return map;
    }


    /**
     * 获取泛型类，比如USER<T>
     * @return 返回T
     */
    private Class<?>  getEntityClz(Class clz){

        Type t = clz.getGenericInterfaces()[0];
        if(t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            return (Class<?>) p[0];// 获取第一个类型参数的真实类型
        }

        return null;
    }

    /**
     * 获取指定方法的返回值的泛型信息，比如List<User> listUser();获取到的类就是User
     * @param method
     * @return
     */
    public Class<?> getMethodReturnType(Method method){
        Type type = method.getGenericReturnType();// 获取返回值类型
        if (type instanceof ParameterizedType) { // 判断获取的类型是否是参数类型
            Type[] returnTypes = ((ParameterizedType) type).getActualTypeArguments();// 强制转型为带参数的泛型类型，
            // getActualTypeArguments()方法获取类型中的实际类型，如map<String,Integer>中的
            // String，integer因为可能是多个，所以使用数组
          return (Class<?>)returnTypes[0];
        }
        return null;
    }


private void covertJsonObject2Bean(Method method,Object result){
    ReflectionUtils.doWithFields(result.getClass(),field -> {
        ReflectionUtils.makeAccessible(field);
        Object value = field.get(result);
        if(isCollectionOrIsMapOrIsArrays(value)){
            Class returnType = getMethodReturnType(method);
            String json = JSON.toJSONString(value);
            value = JSON.parseArray(json,returnType);
            field.set(result,value);
        }

    });
}




}
