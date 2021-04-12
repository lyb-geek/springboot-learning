package com.github.lybgeek.sentinel.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.github.lybgeek.common.model.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 自定义sentinel异常处理器
 *
 */
@Component
@Slf4j
public class CustomBlockExceptionHandler implements BlockExceptionHandler {
    @Override

    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        response.setStatus(200);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        StringBuffer url = request.getRequestURL();
        if ("GET".equals(request.getMethod()) && StringUtil.isNotBlank(request.getQueryString())) {
            url.append("?").append(request.getQueryString());
        }

        String msg = "Blocked by Sentinel Flow Limit";
        log.error("sentinel flow limit url:{}",url);

        if(e instanceof AuthorityException){
            msg = "Blocked by Sentinel Authority Limit";
        }else if(e instanceof SystemBlockException){
            msg = "Blocked by Sentinel System Limit";
        }else if(e instanceof DegradeException){
            msg = "Blocked by Sentinel degrade Limit";
        }

        AjaxResult result = new AjaxResult();
        result.setSuccess(false);
        result.setCode(429);
        result.setMessage(msg);
        PrintWriter out = response.getWriter();
        out.print(JSON.toJSONString(result));
        out.flush();
        out.close();
    }
}
