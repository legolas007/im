package com.usher.service.impl;

import com.usher.pojo.vo.MyFriendsVO;
import com.usher.service.SearchService;
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
public class SearchServiceImplTest {

    @Autowired
    private SearchService searchService;

    @Test
    public void preconditionSearchFriends() {
    }

    @Test
    public void queryUserInfoByUsername() {
    }

    @Test
    public void sendFriendRequest() {
    }

    @Test
    public void queryFriendRequestList() {
    }

    @Test
    public void deleteFriendRequest() {
    }

    @Test
    public void passFriendRequest() {
        searchService.passFriendRequest("1811188M26H5AAY8", "1811188M7W4FNC00");
    }

    @Test
    public void saveFriends() {

    }

    @Test
    public void queryMyFriends() {
        List<MyFriendsVO> list =  searchService.queryMyFriends("1811188M26H5AAY8");
        System.out.println(list);
        Assert.assertNotNull(list);
    }
}
