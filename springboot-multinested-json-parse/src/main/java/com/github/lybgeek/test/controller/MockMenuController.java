package com.github.lybgeek.test.controller;


import com.github.lybgeek.test.model.MenuResourceDTO;
import com.github.lybgeek.test.service.MockMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("menu")
@RequiredArgsConstructor
public class MockMenuController {

    private final MockMenuService mockMenuService;


    @GetMapping
    public MenuResourceDTO getMenu(){
        return mockMenuService.getMenuResourceDTO();
    }
}
