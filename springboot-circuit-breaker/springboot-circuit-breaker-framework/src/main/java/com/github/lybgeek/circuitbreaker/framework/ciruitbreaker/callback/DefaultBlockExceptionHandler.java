/*
 * Copyright 1999-2019 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.lybgeek.circuitbreaker.framework.ciruitbreaker.callback;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.github.lybgeek.circuitbreaker.framework.ciruitbreaker.model.AjaxResult;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.PrintWriter;


@Slf4j
public class DefaultBlockExceptionHandler implements BlockExceptionHandler {

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
            msg = "Blocked by Sentinel Degrade Limit";
        }else if(e instanceof ParamFlowException){
            msg = "Blocked by Sentinel ParamFlow Limit";
        }

        AjaxResult result = new AjaxResult();
        result.setSuccess(false);
        result.setCode(429);
        result.setMessage(msg);

        OutputStream out = response.getOutputStream();
        out.write(JSON.toJSONString(result).getBytes("utf-8"));
        out.flush();
        out.close();
    }

}
