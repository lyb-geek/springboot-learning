package com.github.lybgeek.test.controller;


import com.github.lybgeek.advisor.annotation.InjectId;
import com.github.lybgeek.model.Member;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("member")
public class MemberController {

    @PostMapping("add")
    public Member add(@RequestBody @InjectId Member member){
        return member;
    }
}
