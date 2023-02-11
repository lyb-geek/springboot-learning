package com.github.lybgeek.test;


import com.github.lybgeek.test.model.User;
import com.github.lybgeek.test.service.EchoService;
import com.github.lybgeek.test.service.HelloService;
import com.github.lybgeek.test.service.UserService;

public class AptAstMainTest {

    public static void main(String[] args) {

        EchoService echoService = new EchoService();
        echoService.echo("echo");

        System.out.println(new HelloService().sayHello("zhangsan"));

        User user = User.builder().id("1").username("lisi").build();
        System.out.println(new UserService().opsUser(user,true));

    }
}
