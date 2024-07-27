package com.github.lybgeek.test;


import cn.hutool.json.JSONUtil;
import com.dtflys.forest.Forest;
import com.github.lybgeek.user.model.UserDTO;
import com.github.lybgeek.validate.util.SnakeToKebabConverter;
import org.junit.Test;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;


public class UserHttpTest {
    public final static String BASE_URL = "http://localhost:8081/";

    private final ThreadLocalRandom random = ThreadLocalRandom.current();



    @Test
    public void testSave(){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("zhangsan@qq.com");
        userDTO.setPassword("123456");
        userDTO.setUsername("zhangsan");
        userDTO.setMobile("13600000002");
        String language = getLanguage();
        System.out.println("language:"+language);
        String result = Forest.post(BASE_URL + "user/save?lang="+language)
                .contentTypeJson()
                // 当LocaleResolver为AcceptHeaderLocaleResolver，支持header传递Accept-Language，该模式为默认模式
                // 本示例我们改成成通过url传递参数,因此我们需做一定改造，该地方查看com.github.lybgeek.validate.autoconfigure.ValidateAutoConfiguration
                // .addHeader("Accept-Language", language)
                .addBody(JSONUtil.toJsonStr(userDTO)).execute(String.class);
        System.out.println(result);

    }

    @Test
    public void testAdd(){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("lisi@qq.com");
        userDTO.setPassword("123456");
        userDTO.setUsername("lisi");
        userDTO.setMobile("13600000006");
        String language = getLanguage();
        System.out.println("language:"+language);
        String result = Forest.post(BASE_URL + "user/add?lang="+language)
                .contentTypeJson()
                // 当LocaleResolver为AcceptHeaderLocaleResolver，支持header传递Accept-Language，该模式为默认模式
                // 本示例我们改成成通过url传递参数,因此我们需做一定改造，该地方查看com.github.lybgeek.validate.autoconfigure.ValidateAutoConfiguration
                // .addHeader("Accept-Language", language)
                .addBody(JSONUtil.toJsonStr(userDTO)).execute(String.class);
        System.out.println(result);

    }


    @Test
    public void testUpdate(){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("zhangsan@qq.com");
        userDTO.setPassword("12345678");
        userDTO.setUsername("zhangsan");
        userDTO.setMobile("13600000022");
        userDTO.setId(1L);
        String language = getLanguage();
        System.out.println("language:"+language);
        String result = Forest.post(BASE_URL + "user/update?lang="+language)
                .contentTypeJson()
                // 当LocaleResolver为AcceptHeaderLocaleResolver，支持header传递Accept-Language，该模式为默认模式
                // 本示例我们改成成通过url传递参数,因此我们需做一定改造，该地方查看com.github.lybgeek.validate.autoconfigure.ValidateAutoConfiguration
                // .addHeader("Accept-Language", language)
                .addBody(JSONUtil.toJsonStr(userDTO)).execute(String.class);
        System.out.println(result);

    }


    @Test
    public void testSaveErrorWithI18n(){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("123");
        userDTO.setPassword("123");
        userDTO.setMobile("123");
        String language = getLanguage();
        System.out.println("language:"+language);
        String result = Forest.post(BASE_URL + "user/save?lang="+language)
                .contentTypeJson()
                // 当LocaleResolver为AcceptHeaderLocaleResolver，支持header传递Accept-Language，该模式为默认模式
                // 本示例我们改成成通过url传递参数,因此我们需做一定改造，该地方查看com.github.lybgeek.validate.autoconfigure.ValidateAutoConfiguration
               // .addHeader("Accept-Language", language)
                .addBody(JSONUtil.toJsonStr(userDTO)).execute(String.class);
        System.out.println(result);

    }

    @Test
    public void testAddErrorWithI18n(){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("12345");
        userDTO.setPassword("12345");
        userDTO.setMobile("123");
        String language = getLanguage();
        System.out.println("language:"+language);
        String result = Forest.post(BASE_URL + "user/add?lang="+language)
                .contentTypeJson()
                // 当LocaleResolver为AcceptHeaderLocaleResolver，支持header传递Accept-Language，该模式为默认模式
                // 本示例我们改成成通过url传递参数,因此我们需做一定改造，该地方查看com.github.lybgeek.validate.autoconfigure.ValidateAutoConfiguration
                // .addHeader("Accept-Language", language)
                .addBody(JSONUtil.toJsonStr(userDTO)).execute(String.class);
        System.out.println(result);

    }

    @Test
    public void testRepeatFieldErrorWithI18n() {
        for (int i = 0; i < 2; i++) {
                UserDTO userDTO = new UserDTO();
                userDTO.setEmail("123@qq.com");
                userDTO.setMobile("13600000001");
                userDTO.setUsername("hello");
                userDTO.setPassword("123456");
                String language = getLanguage();
                System.out.println("language:"+language);
                Forest.post(BASE_URL + "user/save?lang=" + language)
                    .contentTypeJson()
                    // 当LocaleResolver为AcceptHeaderLocaleResolver，支持header传递Accept-Language，该模式为默认模式
                    // 本示例我们改成成通过url传递参数,因此我们需做一定改造，该地方查看com.github.lybgeek.validate.autoconfigure.ValidateAutoConfiguration
                    // .addHeader("Accept-Language", language)
                    .addBody(JSONUtil.toJsonStr(userDTO))
                    .onSuccess((data, req, res) -> System.out.println(data))
                    .onError((ex, req, res) -> System.out.println(ex.getMessage()))
                        .execute();

               System.out.println("----------------------------------------------------");


        }

    }


    @Test
    public void testUpdateFieldErrorWithI18n() {
            Long id = getId(random.nextLong(1,3));
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail("123@qq.com");
            userDTO.setMobile("13600000001");
            userDTO.setUsername("hello");
            userDTO.setPassword("123456");
            userDTO.setId(id);
            String language = getLanguage();
            System.out.println("language:"+language);
            Forest.post(BASE_URL + "user/update?lang=" + language)
                    .contentTypeJson()
                    // 当LocaleResolver为AcceptHeaderLocaleResolver，支持header传递Accept-Language，该模式为默认模式
                    // 本示例我们改成成通过url传递参数,因此我们需做一定改造，该地方查看com.github.lybgeek.validate.autoconfigure.ValidateAutoConfiguration
                    // .addHeader("Accept-Language", language)
                    .addBody(JSONUtil.toJsonStr(userDTO))
                    .onSuccess((data, req, res) -> System.out.println(data))
                    .onError((ex, req, res) -> System.out.println(ex.getMessage()))
                    .execute();

            System.out.println("----------------------------------------------------");



    }


    private Long getId(Long id){
        boolean isTrue = random.nextBoolean();
        if(isTrue){
            return id;
        }else{
            return null;
        }

    }


    /**
     * 语言需要用中划线拼接，不能用下划线拼接
     * @return
     */
    private String getLanguage(){
        int i = random.nextInt(3);
        switch (i){
            case 1:
                return SnakeToKebabConverter.snakeToKebab(Locale.US.toString());
            case 2:
                return SnakeToKebabConverter.snakeToKebab(Locale.JAPAN.toString());
            default:
                return SnakeToKebabConverter.snakeToKebab(Locale.CHINA.toString());
        }


    }


}
