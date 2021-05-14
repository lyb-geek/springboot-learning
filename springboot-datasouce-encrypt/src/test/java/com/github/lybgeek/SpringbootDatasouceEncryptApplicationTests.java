package com.github.lybgeek;

import com.github.lybgeek.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringbootDatasouceEncryptApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	public void testList() {
		userService.list().forEach(user-> System.out.println(user));
	}

}
