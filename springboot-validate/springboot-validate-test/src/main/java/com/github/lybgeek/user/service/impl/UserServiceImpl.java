package com.github.lybgeek.user.service.impl;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import com.github.lybgeek.user.convert.UserConvert;
import com.github.lybgeek.user.model.User;
import com.github.lybgeek.user.model.UserDTO;
import com.github.lybgeek.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final static LongAdder ID_LONG_ADDER = new LongAdder();

    private final Map<Long, User> userMap = new ConcurrentHashMap<>();

    private final UserConvert userConvert;

    private final MessageSource messageSource;

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = userConvert.convertDtoToEntity(userDTO);
        ID_LONG_ADDER.increment();
        user.setId(ID_LONG_ADDER.longValue());
        userMap.put(user.getId(),user);

        return userConvert.convertEntityToDto(user);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        Assert.isTrue(userMap.containsKey(userDTO.getId()),messageSource.getMessage("message.user.not.exist",new Object[]{userDTO.getId()}, LocaleContextHolder.getLocale()));
        User user = userConvert.convertDtoToEntity(userDTO);
        userMap.put(user.getId(),user);
        return userConvert.convertEntityToDto(user);
    }


    @Override
    public boolean checkUnique(Object value, String... checkFields) {
        if(MapUtil.isEmpty(userMap)){
            return false;
        }
        if(ArrayUtil.isNotEmpty(checkFields)){
            switch (checkFields[0]){
                case "mobile":
                    return userMap.values().stream().anyMatch(user -> user.getMobile().equals(String.valueOf(value)));
                case "username":
                    return userMap.values().stream().anyMatch(user -> user.getUsername().equals(String.valueOf(value)));
                default:
                    return false;
            }

        }

        return userMap.values().stream().anyMatch(user -> user.getMobile().equals(String.valueOf(value)));

    }



}
