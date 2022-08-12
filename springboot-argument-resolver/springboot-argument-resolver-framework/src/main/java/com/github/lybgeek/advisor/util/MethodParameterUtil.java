package com.github.lybgeek.advisor.util;


import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSON;
import com.github.lybgeek.advisor.annotation.InjectId;
import com.github.lybgeek.constant.Constant;
import com.github.lybgeek.model.MetaInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public final class MethodParameterUtil {

    private MethodParameterUtil(){}

    public static void fillParamValueWithId(MethodParameter[] methodParameters,Object[] args,Class<? extends MetaInfo> clz){
        if(ArrayUtil.isNotEmpty(methodParameters)){
            for (MethodParameter methodParameter : methodParameters) {
                if (methodParameter.getParameterType().isAssignableFrom(clz)
                        && methodParameter.hasParameterAnnotation(InjectId.class)) {
                    Object obj = args[methodParameter.getParameterIndex()];
                    if(obj instanceof MetaInfo){
                        MetaInfo metaInfo = (MetaInfo) obj;
                        if(StringUtils.isBlank(metaInfo.getId())){
                            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                            String id = servletRequestAttributes.getRequest().getHeader(Constant.HEADER_KEY_ID);
                            metaInfo.setId(id);

                            System.out.println(">>>>>>>>>>>>> newObj----> " + JSON.toJSONString(obj));
                        }
                    }


                }
            }
        }
    }
}
