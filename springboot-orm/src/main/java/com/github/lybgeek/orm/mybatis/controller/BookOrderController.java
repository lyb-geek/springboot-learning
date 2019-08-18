package com.github.lybgeek.orm.mybatis.controller;

import com.github.lybgeek.orm.common.model.PageQuery;
import com.github.lybgeek.orm.common.model.PageResult;
import com.github.lybgeek.orm.common.model.Result;
import com.github.lybgeek.orm.common.util.ResultUtil;
import com.github.lybgeek.orm.mybatis.dto.BookOrderDTO;
import com.github.lybgeek.orm.mybatis.service.BookOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
public class BookOrderController {

    @Autowired
    private BookOrderService bookOrderService;


    @PostMapping(value="/create")
    public Result<BookOrderDTO> createBookOrder(@Valid  BookOrderDTO bookOrderDTO, BindingResult bindingResult){
        Result<BookOrderDTO> result = new Result<>();
        if (bindingResult.hasErrors()){
            return ResultUtil.INSTANCE.getFailResult(bindingResult, result);
        }
        try {
            BookOrderDTO bookOrder = bookOrderService.createBookOrder(bookOrderDTO);
            result.setData(bookOrder);
        } catch (Exception e) {
            log.error("createBookOrder error:"+e.getMessage(),e);
            result.setStatus(Result.fail);
            result.setMessage(e.getMessage());
        }

        return result;

    }


    @PostMapping(value="/update")
    public Result<BookOrderDTO> upadteBookOrder(BookOrderDTO bookOrderDTO){
        Result<BookOrderDTO> result = new Result<>();
        if(bookOrderDTO.getId() == null){
            result.setStatus(Result.fail);
            result.setMessage("id不能为空");
            return result;
        }
        BookOrderDTO bookOrder = bookOrderService.editBookOrder(bookOrderDTO);
        result.setData(bookOrder);
        return result;


    }


    @PostMapping(value="/del/{id}")
    public Result<Boolean> delBookOrder(@PathVariable("id")Long id){
        Result<Boolean> result = new Result<>();
        Boolean delFlag = bookOrderService.delBookOrderById(id);
        result.setData(delFlag);
        return result;

    }

    @PostMapping(value="/list")
    public Result<List<BookOrderDTO>> listBookOrders(BookOrderDTO bookOrderDTO){
        Result<List<BookOrderDTO>> result = new Result<>();
        List<BookOrderDTO> bookOrders = bookOrderService.listBookOrders(bookOrderDTO);
        result.setData(bookOrders);
        return result;
    }

    @PostMapping(value="/page")
    public Result<PageResult<BookOrderDTO>> pageOrders(BookOrderDTO bookOrderDTO, Integer pageNo, Integer pageSize){

        PageQuery<BookOrderDTO> pageQuery = new PageQuery<>();
        pageQuery.setPageNo(pageNo);
        pageQuery.setPageSize(pageSize);
        pageQuery.setQueryParams(bookOrderDTO);
        Result<PageResult<BookOrderDTO>> result = new Result<>();
        PageResult<BookOrderDTO> bookOrders = bookOrderService.pageBookOrder(pageQuery);
        result.setData(bookOrders);
        return result;
    }
}
