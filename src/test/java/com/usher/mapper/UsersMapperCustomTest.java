package com.usher.mapper;

import com.usher.pojo.vo.MyFriendsVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: Usher
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersMapperCustomTest {

    @Autowired
    private UsersMapperCustom usersMapperCustom;
    @Test
    public void queryFriendRequestList() {
    }

    @Test
    public void queryMyFriends() {
        List<MyFriendsVO> list = usersMapperCustom.queryMyFriends("1811188M26H5AAY8");
        Assert.assertNotNull(list);

    }
}
