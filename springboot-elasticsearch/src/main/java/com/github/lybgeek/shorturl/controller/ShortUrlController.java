package com.github.lybgeek.shorturl.controller;

import com.github.lybgeek.common.model.PageQuery;
import com.github.lybgeek.common.model.PageResult;
import com.github.lybgeek.common.model.Result;
import com.github.lybgeek.common.util.ResultUtil;
import com.github.lybgeek.shorturl.dto.ShortUrlDTO;
import com.github.lybgeek.shorturl.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping(value="shortUrl")
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;


    @PostMapping(value="/get")
    @ResponseBody
    public Result<String> saveAndReturnShortUrl(@Valid ShortUrlDTO shortUrlDTO, BindingResult bindingResult){
        Result<String> result = new Result<>();
        if (bindingResult.hasErrors()){
            return ResultUtil.INSTANCE.getFailResult(bindingResult, result);
        }

        String shortUrl = shortUrlService.saveAndReturnShortUrl(shortUrlDTO);
        result.setData(shortUrl);
        return result;
    }


    @PostMapping(value="/page")
    @ResponseBody
    public Result<PageResult<ShortUrlDTO>> pag(ShortUrlDTO shortUrlDTO, Integer pageNo, Integer pageSize){

        PageQuery<ShortUrlDTO> pageQuery = new PageQuery<>();
        pageQuery.setPageNo(pageNo);
        pageQuery.setPageSize(pageSize);
        pageQuery.setQueryParams(shortUrlDTO);
        Result<PageResult<ShortUrlDTO>> result = new Result<>();
        PageResult<ShortUrlDTO> pageResult = shortUrlService.pageShortUrl(pageQuery);
        result.setData(pageResult);
        return result;
    }

    @PostMapping(value="gotoUrl")
    public String  redirect(@RequestParam String shortUrl) throws IOException {
        String longUrl = shortUrlService.getLongUrlByShortUrl(shortUrl);
        return "redirect:"+longUrl;
    }
}
