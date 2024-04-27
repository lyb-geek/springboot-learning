package com.github.lybgeek.jsonview.user.util;


import com.github.javafaker.Faker;

import com.github.lybgeek.jsonview.user.model.Address;
import com.github.lybgeek.jsonview.user.model.User;
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
    public static User generateUser() {
        Faker faker = Faker.instance(Locale.CHINA);
        Random random = new Random();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        String fullName = faker.name().fullName();
        String username = PinyinHelper.toHanYuPinyinString(fullName, format);

        return User.builder().age(random.nextInt(30))
                .email(username + "@qq.com")
                .username(username)
                .fullname(fullName)
                .phone(faker.phoneNumber().phoneNumber())
                .address(generateAddress())
                .build();
    }

    public static Address generateAddress() {
        Faker faker = Faker.instance(Locale.CHINA);
        return Address.builder().country(faker.address().country())
                .fullAddress(faker.address().fullAddress())
                .streetName(faker.address().streetName())
                .city(faker.address().city())
                .build();
    }



}
