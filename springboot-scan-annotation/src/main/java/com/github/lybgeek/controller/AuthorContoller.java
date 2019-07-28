package com.github.lybgeek.controller;

import com.github.lybgeek.dto.AuthorDTO;
import com.github.lybgeek.dto.Result;
import com.github.lybgeek.model.Author;
import com.github.lybgeek.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorContoller {

    @Autowired
    private AuthorService authorService;

    @PostMapping(value="/add")
    public Result<Author> addAuthor(@Valid Author author, BindingResult bindingResult){
        Result<Author> result = new Result<>();
        if (bindingResult.hasErrors()){
            return getAuthorFailResult(bindingResult, result);
        }
        Author dbAuthor = authorService.saveAuthor(author);
        result.setData(dbAuthor);

        return result;

    }


    @PostMapping(value="/update")
    public Result<Author> upadteAuthor(@Valid AuthorDTO authorDTO, BindingResult bindingResult){
        Result<Author> result = new Result<>();
        if (bindingResult.hasErrors()){
            return getAuthorFailResult(bindingResult, result);
        }
        Author dbAuthor = authorService.updateAuthor(authorDTO);
        result.setData(dbAuthor);

        return result;


    }

    @PostMapping(value="/del/{id}")
    public Result<Boolean> delAuthor(@PathVariable("id")Long id){
        Result<Boolean> result = new Result<>();
        Boolean delFlag = authorService.deleteAuthorById(id);
        result.setData(delFlag);
        return result;

    }

    @GetMapping(value="/list")
    public Result<List<Author>> listAuthor(){
        Result<List<Author>> result = new Result<>();
        List<Author> authors = authorService.listAuthors();
        result.setData(authors);
        return result;
    }

    private Result<Author> getAuthorFailResult(BindingResult bindingResult, Result<Author> result) {
        result.setStatus(Result.fail);
        List<ObjectError> errorList = bindingResult.getAllErrors();
        StringBuilder messages = new StringBuilder();
        errorList.forEach(error->{
            messages.append(error.getDefaultMessage()).append(";");
        });
        result.setMessage(messages.toString());
        return result;
    }
}
