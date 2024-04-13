package com.github.lybgeek.user.util;


import com.github.javafaker.Faker;
import com.github.lybgeek.user.model.User;
import com.github.lybgeek.user.model.UserDTO;
import lombok.SneakyThrows;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import java.util.Locale;
import java.util.Random;

public final class UserUtil {

    private UserUtil() {
    }

    @SneakyThrows
    public static UserDTO generateUser() {
        Faker faker = Faker.instance(Locale.CHINA);
        Random random = new Random();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        String fullName = faker.name().fullName();
        String username = PinyinHelper.toHanYuPinyinString(fullName, format);
        return UserDTO.builder().age(random.nextInt(30))
                .email(username + "@qq.com")
                .username(username)
                .fullname(fullName)
                .mobile(faker.phoneNumber().phoneNumber())
                .build();
    }

    public static User generateUser(UserDTO userDTO) {
        return User.builder().age(userDTO.getAge())
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .fullname(userDTO.getFullname())
                .mobile(userDTO.getMobile())
                .build();
    }


}
