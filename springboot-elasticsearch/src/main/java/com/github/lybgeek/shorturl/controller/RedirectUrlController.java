package com.github.lybgeek.shorturl.controller;

import com.github.lybgeek.shorturl.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping(value="/")
public class RedirectUrlController {

    @Autowired
    private ShortUrlService shortUrlService;


    @GetMapping(value="{radixStr}")
    public String  gotoUrl(@PathVariable("radixStr")String radixStr) throws IOException {
        String longUrl = shortUrlService.getLongUrlByRadixStr(radixStr);
        return "redirect:"+longUrl;
    }
}
