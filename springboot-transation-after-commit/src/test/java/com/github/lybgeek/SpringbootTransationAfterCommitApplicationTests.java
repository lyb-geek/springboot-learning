package com.github.lybgeek;

import com.github.lybgeek.user.constant.Constant;
import com.github.lybgeek.user.entity.User;
import com.github.lybgeek.user.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
public class SpringbootTransationAfterCommitApplicationTests {

	@Autowired
	private UserService userService;

	private User userForTestMockNotity;

	private User userForTestMockException;

	@Before
	public void parepereUser(){
        userForTestMockNotity = User.builder().
				email("zhagnsan@qq.com").
				fullname("张三")
				.username("zhangsan")
				.mobile("13600000000")
				.password("123456")
				.build();

		userForTestMockException = User.builder().
				email(Constant.MOCK_EXCEPTION_USER +"@qq.com").
				fullname(Constant.MOCK_EXCEPTION_USER)
				.username(Constant.MOCK_EXCEPTION_USER)
				.mobile("13600000001")
				.password("123456")
				.build();
	}

	@Test
	public void testSaveAndMockLongTimeNotity(){
		userService.saveAndMockLongTimeNotity(userForTestMockNotity);
		pause();

	}

	@Test
	public void testSaveAndMockLongTimeNotityWithEvent(){
		userService.saveAndMockNotityWithEvent(userForTestMockNotity);
		pause();
	}

	@Test
	public void testSaveAndMockExceptionCall(){
		userService.saveAndMockExceptionCall(userForTestMockException);
		pause();
	}

	@Test
	public void testSaveAndMockExceptionCallWithEvent(){
		userService.saveAndMockNotityWithEvent(userForTestMockException);
		pause();
	}


	private void pause(){
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
