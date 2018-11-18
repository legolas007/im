package com.usher.service;

import com.usher.pojo.Users;
import com.usher.pojo.vo.FriendRequestVO;
import com.usher.pojo.vo.MyFriendsVO;

import java.util.List;

/**
 * @Author: Usher
 * @Description:
 */
public interface SearchService {
    /**
     * 搜索朋友的前置
     * @param myUserId
     * @param friendUsername
     * @return
     */
    Integer preconditionSearchFriends(String myUserId, String friendUsername);

    /**
     * 根据用户名查询对象
     * @param username
     * @return
     */
    Users queryUserInfoByUsername(String username);

    /**
     * 添加好友请求记录，保存到数据库
     * @param myUserId
     * @param friendUsername
     */
    void sendFriendRequest(String myUserId, String friendUsername);

    /**
     * 查询好友请求
     * @param acceptUserId
     * @return
     */
    List<FriendRequestVO> queryFriendRequestList(String acceptUserId);

    /**
     * 删除好友请求记录
     * @param sendUserId
     * @param acceptUserId
     */
    void deleteFriendRequest(String sendUserId, String acceptUserId);
    /**
     * @Description:
     * 通过好友请求
     * 	1. 保存好友
     * 	2. 逆向保存好友
     * 	3. 删除好友请求记录
     */
    void passFriendRequest(String sendUserId, String acceptUserId);

    /**
     * @Description:
     * 查询好友列表
     */
    List<MyFriendsVO> queryMyFriends(String userId);
}
