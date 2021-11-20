package com.github.lybgeek.test.controller;


import com.github.lybgeek.desensitization.enums.DesensitizedType;
import com.github.lybgeek.desensitization.service.DesensitizedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class DesensitizedTestController {

    @Autowired
    private DesensitizedService desensitizedService;

    @GetMapping(value = "/{sensitiveWord}")
    public String desensitizedCustom(@PathVariable("sensitiveWord") String sensitiveWord){

        return desensitizedService.desensitized(sensitiveWord,"*");
    }

    @GetMapping(value = "/username/{username}")
    public String desensitizedUsername(@PathVariable("username") String username){
        return desensitizedService.desensitized(username, DesensitizedType.CHINESE_NAME);
    }

    @GetMapping(value = "/bankCard/{bankCard}")
    public String desensitizedBankCard(@PathVariable("bankCard") String bankCard){
        return desensitizedService.desensitized(bankCard, DesensitizedType.BANK_CARD);
    }

    @GetMapping(value = "/idCard/{idCard}")
    public String desensitizedIdCard(@PathVariable("idCard") String idCard){
        return desensitizedService.desensitized(idCard, DesensitizedType.ID_CARD);
    }

    @GetMapping(value = "/phone/{phone}")
    public String desensitizedPhone(@PathVariable("phone") String phone){
        return desensitizedService.desensitized(phone, DesensitizedType.MOBILE_PHONE);
    }

    @GetMapping(value = "/email/{email}")
    public String desensitizedEmail(@PathVariable("email") String email){
        return desensitizedService.desensitized(email, DesensitizedType.EMAIL);
    }
    @GetMapping(value = "/password/{password}")
    public String desensitizedPassword(@PathVariable("password") String password){
        return desensitizedService.desensitized(password, DesensitizedType.PASSWORD);
    }
}
