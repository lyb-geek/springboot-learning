package com.github.lybgeek.user.service;


import com.github.lybgeek.validate.constraint.service.UniqueCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MobileCheckService implements UniqueCheckService {

    private final UserService userService;
    @Override
    public boolean checkUnique(Object value, String... checkFields) {
        return userService.checkUnique(value,checkFields);
    }
}
