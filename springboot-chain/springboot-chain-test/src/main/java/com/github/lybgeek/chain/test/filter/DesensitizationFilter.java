package com.github.lybgeek.chain.test.filter;


import cn.hutool.json.JSONUtil;
import com.github.lybgeek.chain.test.user.dto.UserDTO;
import com.github.lybgeek.chain.test.util.DesensitizationUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.Filter;
import org.apache.commons.chain.web.servlet.ServletWebContext;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
public class DesensitizationFilter implements Filter {
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public boolean postprocess(Context context, Exception exception) {
        if(exception != null){
            log.error("desensitization error", exception);
        }
        return exception == null;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        if(context instanceof ServletWebContext){
            ServletWebContext servletWebContext = (ServletWebContext) context;
            HttpServletRequest request = servletWebContext.getRequest();
            if(isMatchPath(request)){
                Object userDTO = servletWebContext.get("user");
                if(userDTO instanceof UserDTO){
                    UserDTO user = (UserDTO) userDTO;
                    user.setMobile(DesensitizationUtil.desensitizeMobile(user.getMobile()));
                    user.setUsername(DesensitizationUtil.desensitizeName(user.getUsername()));
                    servletWebContext.getResponse().setContentType("application/json;charset=UTF-8");
                    servletWebContext.getResponse().getWriter().write(JSONUtil.toJsonStr(userDTO));
                    return true;
                }
            }

        }

        return false;
    }


    private boolean isMatchPath(HttpServletRequest request){

        if(antPathMatcher.match("/user/{id}", request.getRequestURI())){
            Map<String, String> pathValueMap = antPathMatcher.extractUriTemplateVariables("/user/{id}", request.getRequestURI());
            if(!pathValueMap.containsKey("id")){
                return false;
            }

            String value = pathValueMap.get("id");
            if(!value.matches("^\\d+$")){
                return false;
            }

            return true;
        }

        return false;

    }
}
