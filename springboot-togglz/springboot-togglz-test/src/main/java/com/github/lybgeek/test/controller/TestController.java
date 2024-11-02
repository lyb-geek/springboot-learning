package com.github.lybgeek.test.controller;


import com.github.lybgeek.togglz.env.annotation.EnvTogglz;
import com.github.lybgeek.togglz.env.feature.EnvFeature;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@RequiredArgsConstructor
public class TestController {



    @GetMapping("info")
    @EnvTogglz
    public ResponseEntity<String> info(){
        return ResponseEntity.ok("生产环境-资源");
    }
}
