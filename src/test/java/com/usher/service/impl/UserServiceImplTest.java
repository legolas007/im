package com.usher.service.impl;

import com.usher.pojo.Users;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;

/**
 * @Author: Usher
 * @Description:
 * Service层测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userService;


    @Test
    public void queryUsernameIsExist() {
        boolean f = userService.queryUsernameIsExist("usher");
        Assert.assertFalse(f);
    }
    @Test
    public void queryUserForLogin() {
        String username = "user", pwd = "123456";
        Users users = userService.queryUserForLogin(username, pwd);
        System.out.println(users);
    }
}
