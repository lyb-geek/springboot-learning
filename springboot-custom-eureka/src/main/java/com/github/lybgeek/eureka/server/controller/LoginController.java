package com.github.lybgeek.eureka.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 登录
 *
 **/
@Controller
@Slf4j
public class LoginController {

    @GetMapping("/login/error")
    public String loginError(HttpServletRequest request) {
        AuthenticationException authenticationException = (AuthenticationException) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        log.info("authenticationException={}", authenticationException);
        String errorMsg;

        if (authenticationException instanceof UsernameNotFoundException || authenticationException instanceof BadCredentialsException) {
            errorMsg = "用户名或密码错误";
        } else if (authenticationException instanceof DisabledException) {
            errorMsg = "用户已被禁用";
        } else if (authenticationException instanceof LockedException) {
            errorMsg = "账户被锁定";
        } else if (authenticationException instanceof AccountExpiredException) {
            errorMsg = "账户过期";
        } else if (authenticationException instanceof CredentialsExpiredException) {
            errorMsg = "证书过期";
        } else {
            errorMsg = "登录失败";
        }
        request.setAttribute("errorMsg",errorMsg);
        return "login";
    }


    @GetMapping("/toLogin")
    public String login(HttpServletRequest request) {
        return "login";
    }

}
