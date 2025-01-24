package com.github.lybgeek.chain.test.filter;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.Filter;
import org.apache.commons.chain.web.servlet.ServletWebContext;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class ValidateFilter implements Filter {
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public boolean postprocess(Context context, Exception exception) {
        if(exception != null){
            log.error("validate error", exception);
        }
        return exception == null;
    }

    @Override
    public boolean execute(Context context) throws Exception {

        if(context instanceof ServletWebContext){
            ServletWebContext servletWebContext = (ServletWebContext) context;
            HttpServletRequest request = servletWebContext.getRequest();
            if(antPathMatcher.match("/user/save", request.getRequestURI())){
                checkParams(request, "username");
                checkParams(request, "mobile");
                return true;
            }

        }

        return false;
    }


    private void checkParams(HttpServletRequest request,String paramName){
        String paramValue = request.getParameter(paramName);
        if(StringUtils.isEmpty(paramValue)){
            throw new IllegalArgumentException(paramName + " can not be empty");
        }
    }
}
